package com.xitaymin.tasktracker.service.impl;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Role;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.user.CreateUserTO;
import com.xitaymin.tasktracker.dto.user.EditUserTO;
import com.xitaymin.tasktracker.dto.user.UserRoleTO;
import com.xitaymin.tasktracker.dto.user.UserViewTO;
import com.xitaymin.tasktracker.service.UserService;
import com.xitaymin.tasktracker.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.service.validators.UserValidator;
import com.xitaymin.tasktracker.service.validators.impl.UserValidatorImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;

//Добавление/удаление ролей. При удалении роли убедиться, что не нарушаются инварианты других сущностей.
// Все операции с участием юзера в других сущностях доступны только для deleted=false.
//Получение пользователя по ID вместе с его тасками и командами.

@Service
public class UserServiceImpl implements UserService {
    public static final String INVALID_ROLE_FOR_TEAM =
            "Role %s can't be set for user with id = %d because it consist in team.";
    private final UserDAO userDAO;
    private final UserValidator userValidator;

    public UserServiceImpl(UserDAO userDAO, UserValidator userValidator) {
        this.userDAO = userDAO;
        this.userValidator = userValidator;
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
        //todo fixIt
        User user = userValidator.validateForUpdate(editUserTO);
        user.setName(editUserTO.getName());
        user.setEmail(editUserTO.getEmail());
        //todo set only changed fields
        userDAO.update(user);
    }

    @Override
    @Transactional
    public void deleteUser(long id) {
        User user = userDAO.findOne(id);
        if (userValidator.isUnavailable(user)) {
            throw new NotFoundResourceException(String.format(UserValidatorImpl.USER_NOT_FOUND, id));
        } else {
            user.setDeleted(true);
            userDAO.update(user);
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
        User user = userDAO.findOne(roleTO.getId());
        if (user == null) {
            throw new NotFoundResourceException(String.format(UserValidatorImpl.USER_NOT_FOUND, roleTO.getId()));
        }
        Role role = roleTO.getRole();
        boolean inTeam = user.getTeam() != null;
        if ((role.equals(Role.ADMIN) || role.equals(Role.MANAGER) && inTeam)) {
            throw new InvalidRequestParameterException(String.format(INVALID_ROLE_FOR_TEAM, role, user.getId()));
        }
        //todo add check for second lead in team
        user.getRoles().add(roleTO.getRole());
    }

    @Transactional
    @Override
    public void deleteRole(UserRoleTO roleTO) {
        User user = userDAO.findOne(roleTO.getId());
        if (user == null) {
            throw new NotFoundResourceException(String.format(UserValidatorImpl.USER_NOT_FOUND, roleTO.getId()));
        }
//        if (user.getRoles().contains(roleTO.getRole())) {
//
//        }
        //todo implement

    }


    private UserViewTO convertToTO(User user) {
        return new UserViewTO(user.getId(), user.getName(), user.getEmail(), user.isDeleted(), user.getRoles());
    }

}
