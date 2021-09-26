package com.xitaymin.tasktracker;

import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.ResponseError;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import static com.xitaymin.tasktracker.model.validators.impl.UserValidatorImpl.USER_NOT_FOUND;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserTestSuite extends TaskTrackerSpringContextTestSuite {

    public static final String USERS = "/users";

    @Test
    public void notFound() throws Exception {
        String errorMessage = mockMvc.perform(delete("/users/{id}", 123).accept(APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn().getResponse().getContentAsString();
        ResponseError error = new ResponseError(String.format(USER_NOT_FOUND, 123));
        Assertions.assertThat(errorMessage).isEqualTo(asJson(error));
    }

    @Test
    public void createUser() throws Exception {
        mockMvc.perform(get(USERS).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));

        User request = new User(0, "User Name", "admin@gmail.com", false);
        MvcResult result = mockMvc.perform(post(USERS).content(asJson(request))
                                                   .contentType(APPLICATION_JSON).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        User response = fromResponse(result, User.class);

        Assertions.assertThat(response.getId()).isNotNull();
        Assertions.assertThat(response.getName()).isEqualTo(request.getName());
        Assertions.assertThat(response.getEmail()).isEqualTo(request.getEmail());

        MvcResult resultAfterSave = mockMvc.perform(get(USERS).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        User[] allAfterSave = fromResponse(resultAfterSave, User[].class);
        Assertions.assertThat(allAfterSave).hasSize(1);
        User theOne = allAfterSave[0];
        Assertions.assertThat(theOne.getId()).isNotNull();
        Assertions.assertThat(theOne.getName()).isEqualTo(request.getName());
        Assertions.assertThat(theOne.getEmail()).isEqualTo(request.getEmail());
        Assertions.assertThat(theOne.isDeleted()).isFalse();
    }

    @Test
    public void deleteUser() throws Exception {
        createUser();
        MvcResult resultAfterSave = mockMvc.perform(get(USERS).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        User[] allAfterSave = fromResponse(resultAfterSave, User[].class);
        User user = allAfterSave[0];

        mockMvc.perform(delete(USERS + "/" + user.getId()).accept(APPLICATION_JSON))
                .andExpect(status().isOk());
        mockMvc.perform(get(USERS).accept(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json("[]"));
    }
}
