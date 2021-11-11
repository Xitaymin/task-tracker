package com.xitaymin.tasktracker;

import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.ResponseError;
import org.junit.jupiter.api.Test;
import org.mockito.internal.matchers.apachecommons.ReflectionEquals;
import org.springframework.test.web.servlet.MvcResult;

import static com.xitaymin.tasktracker.controller.UserController.USERS;
import static com.xitaymin.tasktracker.model.service.validators.impl.UserValidatorImpl.USER_NOT_FOUND;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserTestSuite extends TaskTrackerSpringContextTestSuite {

    @Test
    public void notFound() throws Exception {
        String errorMessage = mockMvc.perform(delete("/users/{id}", 123).accept(APPLICATION_JSON)).andExpect(status().isNotFound()).andReturn().getResponse()
                .getContentAsString();
        ResponseError error = new ResponseError(String.format(USER_NOT_FOUND, 123));
        assertThat(errorMessage).isEqualTo(asJson(error));
    }

    @Test
    public void createUser() throws Exception {
        User userFromRequest = new User(0, "User Name", "admin@gmail.com", false);
        mockMvc.perform(get(USERS).accept(APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().json("[]"));
        MvcResult result = mockMvc.perform(post(USERS).content(asJson(userFromRequest)).contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        User userFromResponse = fromResponse(result, User.class);
        assertThat(userFromResponse.getId()).isEqualTo(1);
        assertTrue(new ReflectionEquals(userFromRequest, "id").matches(userFromResponse));
        MvcResult resultAfterSave = mockMvc.perform(get(USERS).accept(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        User[] allUsersAfterSave = fromResponse(resultAfterSave, User[].class);
        assertThat(allUsersAfterSave).hasSize(1);
        User userAfterSave = allUsersAfterSave[0];
        assertThat(userAfterSave.getId()).isEqualTo(1);
        assertTrue(new ReflectionEquals(userFromRequest, "id").matches(userAfterSave));
        assertThat(userAfterSave.isDeleted()).isFalse();
    }

    @Test
    public void deleteUser() throws Exception {
        createUser();
        MvcResult resultAfterSave = mockMvc.perform(get(USERS).accept(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        User[] allUsersAfterSave = fromResponse(resultAfterSave, User[].class);
        User userBeforeDelete = allUsersAfterSave[0];

        mockMvc.perform(delete(USERS + "/" + userBeforeDelete.getId()).accept(APPLICATION_JSON)).andExpect(status().isOk());
        mockMvc.perform(get(USERS).accept(APPLICATION_JSON)).andExpect(status().isOk()).andExpect(content().json("[]"));
    }

    @Test
    public void updateUser() throws Exception {
        createUser();
        User userBeforeUpdate = new User(1, "New name", "admin@gmail.com", false);
        mockMvc.perform(put(USERS).content(asJson(userBeforeUpdate)).contentType(APPLICATION_JSON).accept(APPLICATION_JSON)).andExpect(status().isOk());
        MvcResult resultAfterUpdate = mockMvc.perform(get(USERS).accept(APPLICATION_JSON)).andExpect(status().isOk()).andReturn();
        User[] allUsersAfterSave = fromResponse(resultAfterUpdate, User[].class);
        assertThat(allUsersAfterSave).hasSize(1);
        User userAfterUpdate = allUsersAfterSave[0];

        assertThat(userBeforeUpdate).usingRecursiveComparison().isEqualTo(userAfterUpdate);
    }

}
