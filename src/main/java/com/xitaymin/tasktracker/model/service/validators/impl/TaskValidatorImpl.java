package com.xitaymin.tasktracker.model.service.validators.impl;

import com.xitaymin.tasktracker.dao.ProjectDao;
import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.TaskType;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.task.CreateTaskTO;
import com.xitaymin.tasktracker.model.dto.task.TaskBuilder;
import com.xitaymin.tasktracker.model.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.service.validators.TaskValidator;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskValidatorImpl implements TaskValidator {
    public static final String REQUIRED_TITLE = "Title is required and shouldn't be empty.";
    public static final String TASK_NOT_FOUND = "Task with id = %s doesn't exist.";
    public static final String REPORTER_SHOULDNT_CHANGE = "Reporter shouldn't be changed. Old value = %s.";
    public static final String ASSIGNEE_SHOULDNT_CHANGE =
            "Assignee shouldn't be changed in this request. Old value = %s.";
    public static final String REQUIRED_DESCRIPTION = "Description is required and shouldn't be empty.";
    public static final String REPORTER_NOT_FOUND = "Not found reporter with id = %s.";
    public static final String ASSIGNEE_NOT_FOUND = "Not found assignee with id = %s.";
    public static final String PROJECT_DOESNT_EXIST = "Project with id = %d doesn't exist.";
    public static final String ASSIGNEE_NOT_IN_TEAM =
            "Assignee with id = %d should consist in team related to the project with id = %d";
    public static final String NO_NEED_PARENT_FOR_EPIC = "Task with type EPIC don't need parent.";
    public static final String NOT_FOUND_PARENT_TASK = "Parent task with id = %d doesn't exist.";
    public static final String SUBTASK_WITHOUT_PARENT = "Task with type SUBTASK can't be created without parent;";
    public static final String INCOMPATIBLE_PARENT_TYPE =
            "Task with type %s can't have parent with type %s. Types hierarchies are: EPIC -> STORY -> ISSUE -> " + "SUBTASK and EPIC -> BUG. Every of the task type can have only next level type parent.";
    private final TaskBuilder taskBuilder;
    private final TaskDAO taskDAO;
    private final UserDAO userDAO;
    private final ProjectDao projectDao;

    public TaskValidatorImpl(TaskDAO taskDAO, UserDAO userDAO, ProjectDao projectDao) {
        this.taskBuilder = TaskBuilder.aTask();
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
        this.projectDao = projectDao;
    }

    @Override
    public void validateForUpdate(Task task) {
//        Task oldTask = taskDAO.findOne(task.getId());
//        if (oldTask != null) {
//            if (task.getReporter() != oldTask.getReporter()) {
//                throw new InvalidRequestParameterException(String.format(REPORTER_SHOULDNT_CHANGE,
//                        oldTask.getReporter()));
//            }
//            if (!isAssigneeValidForUpdate(task.getAssignee(), oldTask.getAssignee())) {
//                throw new InvalidRequestParameterException(String.format(ASSIGNEE_SHOULDNT_CHANGE,
//                        oldTask.getAssignee()));
//            }
//
//            if (isTextFieldAbsent(task.getTitle())) {
//                throw new InvalidRequestParameterException(REQUIRED_TITLE);
//            }
//            if (isTextFieldAbsent(task.getDescription())) {
//                throw new InvalidRequestParameterException(REQUIRED_DESCRIPTION);
//            }
//        } else {
//            throw new NotFoundResourceException(String.format(TASK_NOT_FOUND, task.getId()));
//        }
    }

    @Override
    public Task getTaskValidForSave(CreateTaskTO taskTO) {
        taskBuilder.withTitle(taskTO.getTitle());
        taskBuilder.withDescription(taskTO.getDescription());

        Long assigneeId = taskTO.getAssignee();
        long reporterId = taskTO.getReporter();

        Optional<Project> optionalProject = Optional.ofNullable(projectDao.findByIdWithTeams(taskTO.getProjectId()));
        Project project = optionalProject.orElseThrow(() -> new NotFoundResourceException(String.format(
                PROJECT_DOESNT_EXIST,
                taskTO.getProjectId())));
        taskBuilder.withProject(project);

        User reporter = userDAO.findOne(reporterId);
        if (isUserUnavailable(reporter)) {
            throw new NotFoundResourceException(String.format(REPORTER_NOT_FOUND, reporterId));
        }
        taskBuilder.withReporter(reporter);

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
        validateTaskType(type, taskTO.getParentId());
        taskBuilder.withType(type);

//        //not necessary
//        if (isTextFieldAbsent(taskTO.getTitle())) {
//            throw new InvalidRequestParameterException(REQUIRED_TITLE);
//        }
//        if (isTextFieldAbsent(taskTO.getDescription())) {
//            throw new InvalidRequestParameterException(REQUIRED_DESCRIPTION);
//        }

        return taskBuilder.build();


    }

    private void validateTaskType(TaskType childType, Long parentId) {
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
        }
    }

//    private boolean isTextFieldAbsent(String text) {
//        return (text == null || text.isBlank());
//    }

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

