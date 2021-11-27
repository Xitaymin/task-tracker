package com.xitaymin.tasktracker.service.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.BaseEntity;
import com.xitaymin.tasktracker.dao.entity.Role;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.Team;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.user.CreateUserTO;
import com.xitaymin.tasktracker.dto.user.EditUserTO;
import com.xitaymin.tasktracker.dto.user.FullUserTO;
import com.xitaymin.tasktracker.dto.user.UserRoleTO;
import com.xitaymin.tasktracker.dto.user.UserViewTO;
import com.xitaymin.tasktracker.service.UserService;
import com.xitaymin.tasktracker.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.service.validators.UserValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static com.xitaymin.tasktracker.service.EntityAbsentUtils.throwExceptionIfAbsent;
import static com.xitaymin.tasktracker.service.validators.impl.TaskValidatorImpl.ASSIGNEE_NOT_FOUND;
import static com.xitaymin.tasktracker.service.validators.impl.TaskValidatorImpl.TASK_NOT_FOUND;
import static com.xitaymin.tasktracker.service.validators.impl.UserValidatorImpl.USER_NOT_FOUND;

@Service
public class UserServiceImpl implements UserService {
    public static final String INVALID_ROLE_FOR_TEAM =
            "Role %s can't be set for user with id = %d because it consist in team.";
    public static final String SECOND_LEAD_IN_TEAM = "Team can't have two members with role LEAD.";
    private final UserDAO userDAO;
    private final UserValidator userValidator;
    private final TaskDAO taskDAO;

    public UserServiceImpl(UserDAO userDAO, UserValidator userValidator, TaskDAO taskDAO) {
        this.userDAO = userDAO;
        this.userValidator = userValidator;
        this.taskDAO = taskDAO;
    }

    @Override
    @Transactional
    public UserViewTO save(CreateUserTO createUserTO) {
        userValidator.validateForSave(createUserTO);
        User user = createUserTO.convertToEntity();
        User savedUser = userDAO.save(user);
        return convertToTO(savedUser);
    }

    @Transactional
    @Override
    public void editUser(EditUserTO editUserTO) {
        User user = userValidator.getUserValidForUpdate(editUserTO);
        user.setName(editUserTO.getName());
        user.setEmail(editUserTO.getEmail());
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        User user = userDAO.findOne(id);
        if (userValidator.isUnavailable(user)) {
            throw new NotFoundResourceException(String.format(USER_NOT_FOUND, id));
        } else {
            user.setDeleted(true);
        }
    }

    @Override
    public Collection<UserViewTO> getAllUsers() {
        Collection<UserViewTO> viewTOS = new HashSet<>();
        for (User user : userDAO.findAll()) {
            viewTOS.add(convertToTO(user));
        }
        return viewTOS;
    }

    @Transactional
    @Override
    public void addRole(UserRoleTO roleTO) {
        User user = userDAO.findFullUserById(roleTO.getId());
        throwExceptionIfAbsent(USER_NOT_FOUND,user,roleTO.getId());

        Role role = roleTO.getRole();

        boolean inTeam = user.getTeam() != null;
        if ((inTeam) && (role.equals(Role.ADMIN) || role.equals(Role.MANAGER))) {
            throw new InvalidRequestParameterException(String.format(INVALID_ROLE_FOR_TEAM, role, user.getId()));
        }
        if (role.equals(Role.LEAD)) {
            Team team = user.getTeam();
            if (team != null) {
                Set<User> members = team.getMembers();
                for (User member : members) {
                    if (member.getRoles().contains(Role.LEAD)) {
                        throw new InvalidRequestParameterException(SECOND_LEAD_IN_TEAM);
                    }
                }
            }
        }
        user.getRoles().add(roleTO.getRole());
    }

    @Transactional
    @Override
    public void deleteRole(UserRoleTO roleTO) {
        long id = roleTO.getId();
        User user = userDAO.findOne(id);
        throwExceptionIfAbsent(USER_NOT_FOUND,user,id);

        Set<Role> userRoles = user.getRoles();
        Role role = roleTO.getRole();
        userRoles.remove(role);
    }


    private UserViewTO convertToTO(User user) {
        return new UserViewTO(user.getId(), user.getName(), user.getEmail(), user.isDeleted(), user.getRoles());
    }

    @Transactional
    @Override
    public void assignTask(long userId, long taskId) {
        Task task = taskDAO.findFullTaskById(taskId);
        throwExceptionIfAbsent(TASK_NOT_FOUND, task, taskId);

        User assignee = userDAO.findOne(userId);
        if (userValidator.isUnavailable(assignee)) {
            throw new NotFoundResourceException(String.format(ASSIGNEE_NOT_FOUND, userId));
        }

        userValidator.validateToAssign(assignee, task);
        task.setAssignee(assignee);
        assignee.getTasks().add(task);
    }


    @Override
    public FullUserTO getById(long id) {
        FullUserTO userTO;
        User user = userDAO.findFullUserById(id);
        if (userValidator.isUnavailable(user)) {
            throw new InvalidRequestParameterException(String.format("User with id = %s not found", id));
        } else {
            userTO = toTO(user);
        }
        return userTO;
    }

    private FullUserTO toTO(User user) {
        Long teamId = null;
        Team team = user.getTeam();
        if (team != null) {
            teamId = team.getId();
        }
        Set<Long> tasksId = user.getTasks().stream().map(BaseEntity::getId).collect(Collectors.toSet());
        return new FullUserTO(user.getId(),
                user.getName(),
                user.getEmail(),
                user.isDeleted(),
                user.getRoles(),
                tasksId,
                teamId);
    }

}
