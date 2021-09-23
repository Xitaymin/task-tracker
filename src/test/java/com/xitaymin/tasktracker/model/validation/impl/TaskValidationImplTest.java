package com.xitaymin.tasktracker.model.validation.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dao.impl.TaskDAOImpl;
import com.xitaymin.tasktracker.dao.impl.UserDAOImpl;
import com.xitaymin.tasktracker.model.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.validation.TaskValidation;
import com.xitaymin.tasktracker.model.validation.UserValidation;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static com.xitaymin.tasktracker.model.validation.impl.TaskValidationImpl.ASSIGNEE_SHOULDNT_CHANGE;
import static com.xitaymin.tasktracker.model.validation.impl.TaskValidationImpl.REPORTER_SHOULDNT_CHANGE;
import static com.xitaymin.tasktracker.model.validation.impl.TaskValidationImpl.REQUIRED_DESCRIPTION;
import static com.xitaymin.tasktracker.model.validation.impl.TaskValidationImpl.REQUIRED_TITLE;
import static com.xitaymin.tasktracker.model.validation.impl.TaskValidationImpl.TASK_NOT_FOUND;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskValidationImplTest {
    private final Task task = mock(Task.class);
    private final String notEmptyString = "some string";
    private final long existingId = 1L;
    private final long anotherExistingId = 2L;
    private final long absentId = 3L;
    TaskDAO taskDAO = mock(TaskDAOImpl.class);
    UserDAO userDAO = mock(UserDAOImpl.class);
    UserValidation userValidation = mock(UserValidationImpl.class);
    Task oldTask = mock(Task.class);
    User user = mock(User.class);
    TaskValidation taskValidation = new TaskValidationImpl(taskDAO, userDAO, userValidation);

    @Test
    public void ifValidationPassWhenSaveValidTask() {
        when(task.getAssignee()).thenReturn(existingId);
        when(task.getReporter()).thenReturn(anotherExistingId);
        when(userDAO.findOne(anyLong())).thenReturn(user);
        when(task.getTitle()).thenReturn(notEmptyString);
        when(task.getDescription()).thenReturn(notEmptyString);

        Assertions.assertTrue(taskValidation.isTaskValidForSave(task));
    }

    @Test
    public void ifValidationFailsWhenUpdateAbsentTask() {
        when(task.getId()).thenReturn(absentId);
        when(taskDAO.findOne(anyLong())).thenReturn(null);

        try {
            taskValidation.isTaskValidForUpdate(task);
        } catch (NotFoundResourceException e) {
            Assertions.assertEquals(e.getMessage(), String.format(TASK_NOT_FOUND, task.getId()));
        }
    }

    @Test
    public void ifValidationFailsWhenUpdateWithChangedReporter() {
        when(task.getId()).thenReturn(existingId);
        when(taskDAO.findOne(anyLong())).thenReturn(oldTask);
        when(task.getReporter()).thenReturn(existingId);
        when(oldTask.getReporter()).thenReturn(anotherExistingId);

        try {
            taskValidation.isTaskValidForUpdate(task);
        } catch (InvalidRequestParameterException e) {
            Assertions.assertEquals(e.getMessage(), String.format(REPORTER_SHOULDNT_CHANGE, oldTask.getReporter()));
        }

    }

    @Test
    public void ifValidationFailsWhenUpdateWithChangedAssignee() {
        when(task.getId()).thenReturn(existingId);
        when(taskDAO.findOne(anyLong())).thenReturn(oldTask);
        when(task.getReporter()).thenReturn(existingId);
        when(oldTask.getReporter()).thenReturn(existingId);
        when(task.getAssignee()).thenReturn(existingId);
        when(oldTask.getAssignee()).thenReturn(anotherExistingId);

        try {
            taskValidation.isTaskValidForUpdate(task);
        } catch (InvalidRequestParameterException e) {
            Assertions.assertEquals(e.getMessage(), String.format(ASSIGNEE_SHOULDNT_CHANGE, oldTask.getAssignee()));
        }
    }

    @Test
    public void ifValidationFailsWhenUpdateWithoutTitle() {
        when(task.getId()).thenReturn(existingId);
        when(taskDAO.findOne(anyLong())).thenReturn(oldTask);
        when(task.getReporter()).thenReturn(existingId);
        when(oldTask.getReporter()).thenReturn(existingId);
        when(task.getAssignee()).thenReturn(existingId);
        when(oldTask.getAssignee()).thenReturn(existingId);
        when(task.getTitle()).thenReturn("");

        try {
            taskValidation.isTaskValidForUpdate(task);
        } catch (InvalidRequestParameterException e) {
            Assertions.assertEquals(e.getMessage(), REQUIRED_TITLE);
        }

        when(task.getTitle()).thenReturn(null);

        try {
            taskValidation.isTaskValidForUpdate(task);
        } catch (InvalidRequestParameterException e) {
            Assertions.assertEquals(e.getMessage(), REQUIRED_TITLE);
        }
    }

    @Test
    public void ifValidationFailsWhenUpdateWithoutDescription() {
        when(task.getId()).thenReturn(existingId);
        when(taskDAO.findOne(anyLong())).thenReturn(oldTask);
        when(task.getReporter()).thenReturn(existingId);
        when(oldTask.getReporter()).thenReturn(existingId);
        when(task.getAssignee()).thenReturn(existingId);
        when(oldTask.getAssignee()).thenReturn(existingId);
        when(task.getTitle()).thenReturn(notEmptyString);
        when(task.getDescription()).thenReturn("");

        try {
            taskValidation.isTaskValidForUpdate(task);
        } catch (InvalidRequestParameterException e) {
            Assertions.assertEquals(e.getMessage(), REQUIRED_DESCRIPTION);
        }

        when(task.getDescription()).thenReturn(null);

        try {
            taskValidation.isTaskValidForUpdate(task);
        } catch (InvalidRequestParameterException e) {
            Assertions.assertEquals(e.getMessage(), REQUIRED_DESCRIPTION);
        }
    }
}