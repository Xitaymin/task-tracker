package com.xitaymin.tasktracker.model.validators.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dao.impl.TaskDAOImpl;
import com.xitaymin.tasktracker.dao.impl.UserDAOImpl;
import com.xitaymin.tasktracker.model.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.validators.TaskValidator;
import org.junit.jupiter.api.Test;

import static com.xitaymin.tasktracker.model.validators.impl.TaskValidatorImpl.ASSIGNEE_SHOULDNT_CHANGE;
import static com.xitaymin.tasktracker.model.validators.impl.TaskValidatorImpl.REPORTER_SHOULDNT_CHANGE;
import static com.xitaymin.tasktracker.model.validators.impl.TaskValidatorImpl.REQUIRED_DESCRIPTION;
import static com.xitaymin.tasktracker.model.validators.impl.TaskValidatorImpl.REQUIRED_TITLE;
import static com.xitaymin.tasktracker.model.validators.impl.TaskValidatorImpl.TASK_NOT_FOUND;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class TaskValidatorImplTest {
    public static final String NOT_EMPTY_STRING = "some string";
    public static final long EXISTING_ID = 1L;
    public static final long ANOTHER_EXISTING_ID = 2L;
    private final Task task = mock(Task.class);
    private final TaskDAO taskDAO = mock(TaskDAOImpl.class);
    private final UserDAO userDAO = mock(UserDAOImpl.class);
    //    private final UserValidation userValidation = mock(UserValidationImpl.class);
    private final User user = mock(User.class);
    private final TaskValidator taskValidator = new TaskValidatorImpl(taskDAO, userDAO);
    private Task taskValidForSave;
    private Task taskValidForUpdate;
    private Task oldTask;

    private void setConditionsForTaskValidForUpdate() {
        taskValidForUpdate = new Task(EXISTING_ID, NOT_EMPTY_STRING, NOT_EMPTY_STRING, EXISTING_ID, ANOTHER_EXISTING_ID);
        oldTask = new Task(EXISTING_ID, NOT_EMPTY_STRING, NOT_EMPTY_STRING, EXISTING_ID, ANOTHER_EXISTING_ID);

        //        when(task.getAssignee()).thenReturn(EXISTING_ID);
        //        when(task.getReporter()).thenReturn(ANOTHER_EXISTING_ID);
        when(taskDAO.findOne(anyLong())).thenReturn(oldTask);
        //        when(oldTask.getAssignee()).thenReturn(EXISTING_ID);
        //        when(oldTask.getReporter()).thenReturn(ANOTHER_EXISTING_ID);
        //        when(task.getTitle()).thenReturn(NOT_EMPTY_STRING);
        //        when(task.getDescription()).thenReturn(NOT_EMPTY_STRING);
    }

    private void setConditionsForTaskValidToSave() {
        taskValidForSave = new Task(0, NOT_EMPTY_STRING, NOT_EMPTY_STRING, EXISTING_ID, ANOTHER_EXISTING_ID);
        when(userDAO.findOne(anyLong())).thenReturn(user);
        //        when(userValidation.isUnavailable(user)).thenReturn(false);
    }


    //    @Test
    //    public void ifValidationPassWhenSaveValidTask() {
    //        setConditionsForTaskValidToSave();
    //        Assertions.assertTrue(taskValidator.validateForSave(taskValidForSave));
    //    }
    //
    //    @Test
    //    public void ifValidationPassWhenUpdateValidTask() {
    //        setConditionsForTaskValidForUpdate();
    //        Assertions.assertTrue(taskValidator.validateForUpdate(taskValidForUpdate));
    //    }

    //    @Test
    //    public void ifValidationFailWhenSaveTaskWithoutReporter() {
    //        setConditionsForTaskValidToSave();
    //        when(userDAO.findOne(anyLong())).thenReturn(null);
    ////        when(userValidation.isUnavailable(null)).thenReturn(true);
    //
    //        Throwable throwable = assertThrows(NotFoundResourceException.class, () -> taskValidator.validateForSave(taskValidForSave));
    //        assertEquals(throwable.getMessage(), String.format(REPORTER_NOT_FOUND, task.getReporter()));
    //    }

    @Test()
    public void ifValidationFailsWhenUpdateAbsentTask() {
        setConditionsForTaskValidForUpdate();
        when(taskDAO.findOne(anyLong())).thenReturn(null);

        Throwable throwable = assertThrows(NotFoundResourceException.class, () -> taskValidator.validateForUpdate(task));
        assertEquals(throwable.getMessage(), String.format(TASK_NOT_FOUND, task.getId()));
    }

    @Test
    public void ifValidationFailsWhenUpdateWithChangedReporter() {
        setConditionsForTaskValidForUpdate();
        when(task.getReporter()).thenReturn(EXISTING_ID);
        when(oldTask.getReporter()).thenReturn(ANOTHER_EXISTING_ID);

        Throwable throwable = assertThrows(InvalidRequestParameterException.class, () -> taskValidator.validateForUpdate(task));
        assertEquals(throwable.getMessage(), String.format(REPORTER_SHOULDNT_CHANGE, oldTask.getReporter()));
    }

    @Test
    public void ifValidationFailsWhenUpdateWithChangedAssignee() {
        setConditionsForTaskValidForUpdate();
        when(task.getAssignee()).thenReturn(EXISTING_ID);
        when(oldTask.getAssignee()).thenReturn(ANOTHER_EXISTING_ID);

        Throwable throwable = assertThrows(InvalidRequestParameterException.class, () -> taskValidator.validateForUpdate(task));
        assertEquals(throwable.getMessage(), String.format(ASSIGNEE_SHOULDNT_CHANGE, oldTask.getAssignee()));
    }

    @Test
    public void ifValidationFailsWhenUpdateWithoutTitle() {
        setConditionsForTaskValidForUpdate();
        when(task.getTitle()).thenReturn("", null);
        Throwable throwable;
        for (int i = 0; i < 2; i++) {
            throwable = assertThrows(InvalidRequestParameterException.class, () -> taskValidator.validateForUpdate(task));
            assertEquals(throwable.getMessage(), REQUIRED_TITLE);
        }
    }

    @Test
    public void ifValidationFailsWhenUpdateWithoutDescription() {
        setConditionsForTaskValidForUpdate();
        when(task.getDescription()).thenReturn("", null);
        Throwable throwable;
        for (int i = 0; i < 2; i++) {
            throwable = assertThrows(InvalidRequestParameterException.class, () -> taskValidator.validateForUpdate(task));
            assertEquals(throwable.getMessage(), REQUIRED_DESCRIPTION);
        }
    }
}