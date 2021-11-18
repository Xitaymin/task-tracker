package com.xitaymin.tasktracker.service.validators.impl;

import com.xitaymin.tasktracker.dao.ProjectDao;
import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.TaskType;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.task.CreateTaskTO;
import com.xitaymin.tasktracker.dto.task.TaskBuilder;
import com.xitaymin.tasktracker.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.service.validators.TaskValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskValidatorImpl implements TaskValidator {
    public static final String TASK_NOT_FOUND = "Task with id = %s doesn't exist.";

    public static final String REPORTER_NOT_FOUND = "Not found reporter with id = %s.";
    public static final String ASSIGNEE_NOT_FOUND = "Not found assignee with id = %s.";
    public static final String PROJECT_DOESNT_EXIST = "Project with id = %d doesn't exist.";
    public static final String ASSIGNEE_NOT_IN_TEAM =
            "Assignee with id = %d should consist in team related to the project with id = %d";
    public static final String NOT_FOUND_PARENT_TASK = "Parent task with id = %d doesn't exist.";
    public static final String SUBTASK_WITHOUT_PARENT = "Task with type SUBTASK can't be created without parent;";
    public static final String INCOMPATIBLE_PARENT_TYPE =
            "Task with type %s can't have parent with type %s. Types hierarchies are: EPIC -> STORY -> ISSUE -> " + "SUBTASK and EPIC -> BUG. Every of the task type can have only next level type parent.";
    private final TaskDAO taskDAO;
    private final UserDAO userDAO;
    private final ProjectDao projectDao;

    public TaskValidatorImpl(TaskDAO taskDAO, UserDAO userDAO, ProjectDao projectDao) {
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
        this.projectDao = projectDao;
    }

    @Override
    public Task getTaskValidForSave(CreateTaskTO taskTO) {
        TaskBuilder taskBuilder = TaskBuilder.aTask();
        taskBuilder.withTitle(taskTO.getTitle());
        taskBuilder.withDescription(taskTO.getDescription());

        Optional<Project> optionalProject = Optional.ofNullable(projectDao.findByIdWithTeams(taskTO.getProjectId()));
        Project project = optionalProject.orElseThrow(() -> new NotFoundResourceException(String.format(
                PROJECT_DOESNT_EXIST,
                taskTO.getProjectId())));
        taskBuilder.withProject(project);

        long reporterId = taskTO.getReporter();
        User reporter = userDAO.findOne(reporterId);
        if (isUserUnavailable(reporter)) {
            throw new NotFoundResourceException(String.format(REPORTER_NOT_FOUND, reporterId));
        }
        taskBuilder.withReporter(reporter);

        Long assigneeId = taskTO.getAssignee();
        User assignee = null;
        if (assigneeId != null) {
            assignee = userDAO.findOne(assigneeId);
            if (isUserUnavailable(assignee)) {
                throw new NotFoundResourceException(String.format(ASSIGNEE_NOT_FOUND, assigneeId));
            }
            boolean isAssigneeInTeam = project.getTeams().contains(assignee.getTeam());
            if (!isAssigneeInTeam) {
                throw new InvalidRequestParameterException(String.format(ASSIGNEE_NOT_IN_TEAM,
                        assigneeId,
                        project.getId()));
            }
        }
        taskBuilder.withAssignee(assignee);

        TaskType type = taskTO.getType();
        setValidTaskType(type, taskTO.getParentId(), taskBuilder);

        return taskBuilder.build();

    }

    private void setValidTaskType(TaskType childType, Long parentId, TaskBuilder taskBuilder) {
        if (parentId == null) {
            if (childType.equals(TaskType.SUBTASK)) {
                throw new InvalidRequestParameterException(SUBTASK_WITHOUT_PARENT);
            }
        } else {
            Optional<Task> optionalTask = Optional.ofNullable(taskDAO.findOne(parentId));
            Task parentTask = optionalTask.orElseThrow(() -> new InvalidRequestParameterException(String.format(
                    NOT_FOUND_PARENT_TASK,
                    parentId)));
            TaskType parentType = parentTask.getType();

            if ((childType.getHierarchyLevel() - parentType.getHierarchyLevel() != 1) || (!parentType.isChildable())) {
                throw new InvalidRequestParameterException(String.format(INCOMPATIBLE_PARENT_TYPE,
                        childType.name(),
                        parentType.name()));
            }
            taskBuilder.withParent(parentTask);
        }
        taskBuilder.withType(childType);
    }

    public boolean isUserUnavailable(User user) {
        return (user == null || user.isDeleted());
    }


    private boolean isAssigneeValidForUpdate(Long assignee, Long oldAssignee) {
        if (oldAssignee == null && assignee == null) {
            return true;
        } else if ((oldAssignee == null) ^ (assignee == null)) {
            return false;
        } else {
            return assignee.equals(oldAssignee);
        }
    }

}

