package com.xitaymin.tasktracker;

//public class UserWithTasksTestSuite extends TaskTrackerSpringContextTestSuite {
//
//
//    @BeforeEach
//    public void setUp() throws Exception {
//        mockMvc.perform(post(USERS).content(asJson(new User(0, "First user", "first@gmail.com", false))).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//        mockMvc.perform(post(TASKS).content(asJson(new Task(0, "First task", "Description", 1, null)))
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//    }
//
//    @Test
//    public void assignTask() throws Exception {
//        mockMvc.perform(put(USERS + "/1/task/1")).andExpect(status().isOk());
//        MvcResult resultAfterSave = mockMvc.perform(get(TASKS).contentType(MediaType.APPLICATION_JSON).accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andReturn();
//        Task[] allSavedTasks = fromResponse(resultAfterSave, Task[].class);
//        assertThat(allSavedTasks.length == 1);
//        Task firstSavedTask = allSavedTasks[0];
//        assertEquals(firstSavedTask.getAssignee(), 1);
//    }
//
//    @Test
//    public void getUserWithTasks() throws Exception {
//        assignTask();
//        MvcResult result = mockMvc.perform((get(USERS + "/1").contentType(APPLICATION_JSON).accept(APPLICATION_JSON))).andExpect(status().isOk()).andReturn();
//        UserWithTasks userWithTasks = fromResponse(result, UserWithTasks.class);
//        List<Task> tasks = userWithTasks.getTasks();
//        assertThat(tasks).hasSize(1);
//        assertEquals(tasks.get(0).getId(), 1);
//    }
//}
