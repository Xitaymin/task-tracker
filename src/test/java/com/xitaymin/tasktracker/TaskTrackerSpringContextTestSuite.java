package com.xitaymin.tasktracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xitaymin.tasktracker.utils.Resetable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TaskTrackerApplication.class})
@SpringBootTest
@AutoConfigureMockMvc
//@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD) // do not use it
public abstract class TaskTrackerSpringContextTestSuite {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    private List<Resetable> resetables;

    @AfterEach
    public void clearUp() {
        resetables.forEach(Resetable::reset);
    }

    public String asJson(Object object) throws Exception {
        return new ObjectMapper()
                .writer()
                .withDefaultPrettyPrinter()
                .writeValueAsString(object);
    }

    public <T> T fromResponse(MvcResult result, Class<T> type) throws Exception {
        return new ObjectMapper()
                .readerFor(type)
                .readValue(result.getResponse().getContentAsString());
    }

    public <T> T fromJson(String json, Class<T> type) throws Exception {
        return new ObjectMapper()
                .readerFor(type)
                .readValue(json);
    }
}
