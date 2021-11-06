package com.xitaymin.tasktracker.model.service.impl;

//class TaskServiceImplTest {
//
//    private final TaskDAO taskDAO = mock(TaskDAOImpl.class);
//    private final UserDAO userDAO = mock(UserDAOImpl.class);
//    private final TaskValidator taskValidator = new TaskValidatorImpl(taskDAO, userDAO);
//    private final TaskService taskService = new TaskServiceImpl(taskDAO, taskValidator);
//
//    private static Stream<Arguments> provideTasksWithChangedAssignee() {
//        return Stream.of(
//                Arguments.of(new Task(5, "Title", "Description", 1L, null), new Task(5, "Title", "Description", 1L, 2L)),
//                Arguments.of(new Task(5, "Title", "Description", 1L, 2L), new Task(5, "Title", "Description", 1L, 3L)));
//    }
//
//
//    @AfterEach
//    void tearDown() {
//        reset(userDAO, taskDAO);
//    }
//
//    @Test
//    void ifPassToSaveValidUser() {
//        Task task = new Task(0, "Title", "Description", 1L, null);
//        when(userDAO.findOne(gt(0L))).thenReturn(mock(User.class));
//        taskService.saveTask(task);
//        ArgumentCaptor<Task> taskCaptor = ArgumentCaptor.forClass(Task.class);
//        verify(taskDAO).save(taskCaptor.capture());
//        assertThat(taskCaptor.getValue())
//                .usingRecursiveComparison()
//                .isEqualTo(new Task(0, "Title", "Description", 1L, null));
//    }
//
//
//    @Test
//    void ifFailToSaveTaskWithoutReporter() {
//        Task task = new Task(0, "Title", "Description", 0L, null);
//        when(userDAO.findOne(gt(0L))).thenReturn(mock(User.class));
//        Throwable throwable = assertThrows(NotFoundResourceException.class, () -> taskService.saveTask(task));
//        assertThat(throwable).hasMessage(String.format(REPORTER_NOT_FOUND, task.getReporter()));
//        verify(taskDAO, never()).save(task);
//    }
//
//    @Test
//    void ifFailToSaveTaskWithUnavailableAssignee() {
//        Task task = new Task(0, "Title", "Description", 1L, 2L);
//        when(userDAO.findOne(1L)).thenReturn(mock(User.class));
//        when(userDAO.findOne(2L)).thenReturn(new User(2L, "Name", "Email", true));
//        Throwable throwable = assertThrows(NotFoundResourceException.class, () -> taskService.saveTask(task));
//        assertThat(throwable).hasMessage(String.format(ASSIGNEE_NOT_FOUND, task.getAssignee()));
//        verify(taskDAO, never()).save(any());
//    }
//
//    @Test
//    void ifFailToSaveTaskWithoutTitle() {
//        Task task = new Task(0, "", "Description", 1L, 2L);
//        when(userDAO.findOne(anyLong())).thenReturn(mock(User.class));
//        Throwable throwable = assertThrows(InvalidRequestParameterException.class, () -> taskService.saveTask(task));
//        assertThat(throwable).hasMessage(REQUIRED_TITLE);
//        verify(taskDAO, never()).save(any());
//    }
//
//    @Test
//    void ifFailToSaveTaskWithoutDescription() {
//        Task task = new Task(0, "Title", null, 1L, 2L);
//        when(userDAO.findOne(anyLong())).thenReturn(mock(User.class));
//        Throwable throwable = assertThrows(InvalidRequestParameterException.class, () -> taskService.saveTask(task));
//        assertThat(throwable).hasMessage(REQUIRED_DESCRIPTION);
//        verify(taskDAO, never()).save(any());
//    }
//
//    @ParameterizedTest
//    @MethodSource("provideTasksWithChangedAssignee")
//    void ifFailToUpdateTaskWithInvalidAssignee(Task task, Task oldTask) {
//        when(taskDAO.findOne(task.getId())).thenReturn(oldTask);
//        Throwable throwable = assertThrows(InvalidRequestParameterException.class, () -> taskService.editTask(task));
//        assertThat(throwable).hasMessage(ASSIGNEE_SHOULDNT_CHANGE, oldTask.getAssignee());
//        verify(taskDAO, never()).update(any());
//    }
//
//
//}