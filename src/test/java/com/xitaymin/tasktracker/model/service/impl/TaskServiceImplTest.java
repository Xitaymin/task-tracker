package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dao.impl.TaskDAOImpl;
import com.xitaymin.tasktracker.dao.impl.UserDAOImpl;
import com.xitaymin.tasktracker.model.service.TaskService;
import com.xitaymin.tasktracker.model.service.exceptions.InvalidRequestParameterException;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.validators.TaskValidator;
import com.xitaymin.tasktracker.model.validators.impl.TaskValidatorImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.ArgumentCaptor;

import java.util.stream.Stream;

import static com.xitaymin.tasktracker.model.validators.impl.TaskValidatorImpl.ASSIGNEE_NOT_FOUND;
import static com.xitaymin.tasktracker.model.validators.impl.TaskValidatorImpl.ASSIGNEE_SHOULDNT_CHANGE;
import static com.xitaymin.tasktracker.model.validators.impl.TaskValidatorImpl.REPORTER_NOT_FOUND;
import static com.xitaymin.tasktracker.model.validators.impl.TaskValidatorImpl.REQUIRED_DESCRIPTION;
import static com.xitaymin.tasktracker.model.validators.impl.TaskValidatorImpl.REQUIRED_TITLE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.AdditionalMatchers.gt;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TaskServiceImplTest {

    TaskDAO taskDAO = mock(TaskDAOImpl.class);
    UserDAO userDAO = mock(UserDAOImpl.class);
    TaskValidator taskValidator = new TaskValidatorImpl(taskDAO, userDAO);
    TaskService taskService = new TaskServiceImpl(taskDAO, taskValidator);

    private static Stream<Arguments> provideTasksWithChangedAssignee() {
        return Stream.of(
                Arguments.of(new Task(5, "Title", "Description", 1L, null), new Task(5, "Title", "Description", 1L, 2L)),
                Arguments.of(new Task(5, "Title", "Description", 1L, 2L), new Task(5, "Title", "Description", 1L, 3L)));
    }


    @Test
    void ifPassToSaveValidUser() {
        Task task = new Task(0, "Title", "Description", 1L, null);
        when(userDAO.findOne(gt(0L))).thenReturn(mock(User.class));
        taskService.saveTask(task);
        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
        verify(taskDAO).save(taskCaptor.capture());
        assertThat(taskCaptor.getValue())
                .usingRecursiveComparison()
                .isEqualTo(new Task(0, "Title", "Description", 1L, null));
    }


    @Test
    void ifFailToSaveTaskWithoutReporter() {
        Task task = new Task(0, "Title", "Description", 0L, null);
        when(userDAO.findOne(gt(0L))).thenReturn(mock(User.class));
        Throwable throwable = assertThrows(NotFoundResourceException.class, () -> taskService.saveTask(task));
        assertThat(throwable).hasMessage(String.format(REPORTER_NOT_FOUND, task.getReporter()));
        verify(taskDAO, never()).save(task);
    }

    @Test
    void ifFailToSaveTaskWithUnavailableAssignee() {
        Task task = new Task(0, "Title", "Description", 1L, 2L);
        when(userDAO.findOne(1L)).thenReturn(mock(User.class));
        when(userDAO.findOne(2L)).thenReturn(new User(2L, "Name", "Email", true));
        Throwable throwable = assertThrows(NotFoundResourceException.class, () -> taskService.saveTask(task));
        assertThat(throwable).hasMessage(String.format(ASSIGNEE_NOT_FOUND, task.getAssignee()));
        verify(taskDAO, never()).save(any());
    }

    @Test
    void ifFailToSaveTaskWithoutTitle() {
        Task task = new Task(0, "", "Description", 1L, 2L);
        when(userDAO.findOne(anyLong())).thenReturn(mock(User.class));
        Throwable throwable = assertThrows(InvalidRequestParameterException.class, () -> taskService.saveTask(task));
        assertThat(throwable).hasMessage(REQUIRED_TITLE);
        verify(taskDAO, never()).save(any());
    }

    @Test
    void ifFailToSaveTaskWithoutDescription() {
        Task task = new Task(0, "Title", null, 1L, 2L);
        when(userDAO.findOne(anyLong())).thenReturn(mock(User.class));
        Throwable throwable = assertThrows(InvalidRequestParameterException.class, () -> taskService.saveTask(task));
        assertThat(throwable).hasMessage(REQUIRED_DESCRIPTION);
        verify(taskDAO, never()).save(any());
    }

    @ParameterizedTest
    @MethodSource("provideTasksWithChangedAssignee")
    void ifFailToUpdateTaskWithInvalidAssignee(Task task, Task oldTask) {
        when(taskDAO.findOne(task.getId())).thenReturn(oldTask);
        Throwable throwable = assertThrows(InvalidRequestParameterException.class, () -> taskService.editTask(task));
        assertThat(throwable).hasMessage(ASSIGNEE_SHOULDNT_CHANGE, oldTask.getAssignee());
        verify(taskDAO, never()).update(any());
    }


}