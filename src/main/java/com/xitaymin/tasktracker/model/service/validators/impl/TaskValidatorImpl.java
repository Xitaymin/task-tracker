package com.xitaymin.tasktracker.model.service.validators.impl;

import com.xitaymin.tasktracker.dao.ProjectDao;
import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.TaskType;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.task.CreateTaskTO;
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
    public static final String ASSIGNEE_SHOULDNT_CHANGE = "Assignee shouldn't be changed in this request. Old value = %s.";
    public static final String REQUIRED_DESCRIPTION = "Description is required and shouldn't be empty.";
    public static final String REPORTER_NOT_FOUND = "Not found reporter with id = %s.";
    public static final String ASSIGNEE_NOT_FOUND = "Not found assignee with id = %s.";
    public static final String PROJECT_DOESNT_EXIST = "Project with id = %d doesn't exist.";
    public static final String ASSIGNEE_NOT_IN_TEAM = "Assignee with id = %d should consist in team related to the project with id = %d";
    public static final String NO_NEED_PARENT_FOR_EPIC = "Task with type EPIC don't need parent.";
    private final TaskDAO taskDAO;
    private final UserDAO userDAO;
    private final ProjectDao projectDao;

    public TaskValidatorImpl(TaskDAO taskDAO, UserDAO userDAO, ProjectDao projectDao) {
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
        this.projectDao = projectDao;
    }

    @Override
    public void validateForUpdate(Task task) {
        Task oldTask = taskDAO.findOne(task.getId());
        if (oldTask != null) {
            if (task.getReporter() != oldTask.getReporter()) {
                throw new InvalidRequestParameterException(String.format(REPORTER_SHOULDNT_CHANGE, oldTask.getReporter()));
            }
            if (!isAssigneeValidForUpdate(task.getAssignee(), oldTask.getAssignee())) {
                throw new InvalidRequestParameterException(String.format(ASSIGNEE_SHOULDNT_CHANGE, oldTask.getAssignee()));
            }

            if (isTextFieldAbsent(task.getTitle())) {
                throw new InvalidRequestParameterException(REQUIRED_TITLE);
            }
            if (isTextFieldAbsent(task.getDescription())) {
                throw new InvalidRequestParameterException(REQUIRED_DESCRIPTION);
            }
        } else {
            throw new NotFoundResourceException(String.format(TASK_NOT_FOUND, task.getId()));
        }
    }

    @Override
    public Project validateForSave(CreateTaskTO taskTO) {
        Long assigneeId = taskTO.getAssignee();
        long reporterId = taskTO.getReporter();

        Optional<Project> optionalProject = Optional.ofNullable(projectDao.findByIdWithTeams(taskTO.getProjectId()));
        Project project = optionalProject.orElseThrow(() -> new NotFoundResourceException(String.format(
                PROJECT_DOESNT_EXIST,
                taskTO.getProjectId())));

        if (isUserUnavailable(userDAO.findOne(reporterId))) {
            throw new NotFoundResourceException(String.format(REPORTER_NOT_FOUND, reporterId));
        }
        if (assigneeId != null) {
            User assignee = userDAO.findOne(assigneeId);
            if (isUserUnavailable(assignee)) {
                throw new NotFoundResourceException(String.format(ASSIGNEE_NOT_FOUND, assigneeId));
            }
            boolean isAssigneeInTeam = project.getTeams().contains(assignee.getTeam());
            if(!isAssigneeInTeam){throw new InvalidRequestParameterException(String.format(ASSIGNEE_NOT_IN_TEAM,assigneeId,project.getId()));}
        }
        validateTaskType(taskTO.getType(),taskTO.getParentId());

        //not necessary
        if (isTextFieldAbsent(taskTO.getTitle())) {
            throw new InvalidRequestParameterException(REQUIRED_TITLE);
        }
        if (isTextFieldAbsent(taskTO.getDescription())) {
            throw new InvalidRequestParameterException(REQUIRED_DESCRIPTION);
        }

        return project;


    }

    private void validateTaskType(TaskType type, Long parentId) {
        if(type.equals(TaskType.EPIC)){
            if(parentId!=null){throw new InvalidRequestParameterException(NO_NEED_PARENT_FOR_EPIC);
            }
        }
        else if(type.equals(TaskType.BUG)){
            if(parentId!=null){

                //todo implement
                Task task = taskDAO.findOne(parentId);
                if(!task.getType().equals(TaskType.EPIC)){
                    throw new InvalidRequestParameterException("");
                }
            }
        }
    }

    private boolean isTextFieldAbsent(String text) {
        return (text == null || text.isBlank());
    }

    private boolean isUserUnavailable(User user) {
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

