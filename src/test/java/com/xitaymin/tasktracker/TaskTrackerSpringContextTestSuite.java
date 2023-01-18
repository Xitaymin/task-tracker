package com.xitaymin.tasktracker;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TaskTrackerApplication.class})
@SpringBootTest
@AutoConfigureMockMvc
public abstract class TaskTrackerSpringContextTestSuite {

    @Autowired
    protected MockMvc mockMvc;
//dummy comment
//    @Autowired
//    private List<Resetable> resetables;
//
//    @AfterEach
//    public void clearUp() {
//        resetables.forEach(Resetable::reset);
//    }

    public String asJson(Object object) throws Exception {
        return new ObjectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(object);
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
