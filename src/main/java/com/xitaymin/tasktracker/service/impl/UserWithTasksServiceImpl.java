package com.xitaymin.tasktracker.service.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.PersistentObject;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.user.UserWithTasksAndTeamsTO;
import com.xitaymin.tasktracker.service.GenericService;
import com.xitaymin.tasktracker.service.UserWithTasksService;
import com.xitaymin.tasktracker.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.service.validators.UserValidator;
import com.xitaymin.tasktracker.service.validators.UserWithTasksValidator;
import com.xitaymin.tasktracker.service.validators.impl.TaskValidatorImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Set;
import java.util.stream.Collectors;

import static com.xitaymin.tasktracker.service.validators.impl.TaskValidatorImpl.ASSIGNEE_NOT_FOUND;
import static com.xitaymin.tasktracker.service.validators.impl.TaskValidatorImpl.TASK_NOT_FOUND;

@Service
public class UserWithTasksServiceImpl extends GenericService implements UserWithTasksService {
    private final UserWithTasksValidator userWithTasksValidator;
    private final TaskDAO taskDAO;
    private final UserDAO userDAO;
    private final UserValidator userValidator;

    public UserWithTasksServiceImpl(UserWithTasksValidator userWithTasksValidator, TaskDAO taskDAO, UserDAO userDAO,
                                    UserValidator userValidator) {
        this.userWithTasksValidator = userWithTasksValidator;
        this.taskDAO = taskDAO;
        this.userDAO = userDAO;
        this.userValidator = userValidator;
    }

    @Transactional
    @Override
    public void assignTask(long userId, long taskId) {
        Task task = taskDAO.findFullTask(taskId);
        throwExceptionIfAbsent(TASK_NOT_FOUND,task,taskId);

        User assignee = userDAO.findOne(userId);
        if (userValidator.isUnavailable(assignee)) {
            throw new NotFoundResourceException(String.format(ASSIGNEE_NOT_FOUND, userId));
        }

        userWithTasksValidator.validateToAssign(assignee, task);
        task.setAssignee(assignee);
        assignee.getTasks().add(task);
//        userDAO.update(assignee);
    }


    @Override
    public UserWithTasksAndTeamsTO getById(long id) {
        UserWithTasksAndTeamsTO userTO;
        User user = userDAO.findByIdWithTasksAndTeams(id);
        if (userValidator.isUnavailable(user)) {
            throw new InvalidRequestParameterException(String.format("User with id = %s not found", id));
        } else {
            userTO = toTO(user);
        }
        return userTO;
    }

    private UserWithTasksAndTeamsTO toTO(User user) {
        Set<Long> tasksId = user.getTasks().stream().map(PersistentObject::getId).collect(Collectors.toSet());
        return new UserWithTasksAndTeamsTO(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.isDeleted(),
                user.getRoles(),
                tasksId,
                user.getTeam().getId());
    }
}
