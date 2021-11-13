package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.user.CreateUserTO;
import com.xitaymin.tasktracker.model.dto.user.EditUserTO;
import com.xitaymin.tasktracker.model.dto.user.UserViewTO;
import com.xitaymin.tasktracker.model.service.UserService;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.service.validators.UserValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;

import static com.xitaymin.tasktracker.model.service.validators.impl.UserValidatorImpl.USER_NOT_FOUND;

//Добавление/удаление ролей. При удалении роли убедиться, что не нарушаются инварианты других сущностей.
// Все операции с участием юзера в других сущностях доступны только для deleted=false.
//Получение пользователя по ID вместе с его тасками и командами.

@Service
public class UserServiceImpl implements UserService {
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
            throw new NotFoundResourceException(String.format(USER_NOT_FOUND, id));
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

    private UserViewTO convertToTO(User user) {
        return new UserViewTO(user.getId(), user.getName(), user.getEmail(), user.isDeleted(), user.getRoles());
    }

}
