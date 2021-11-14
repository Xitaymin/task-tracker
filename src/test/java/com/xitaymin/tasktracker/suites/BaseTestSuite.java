package com.xitaymin.tasktracker.suites;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import com.xitaymin.tasktracker.dao.TaskDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest(classes = TestSuiteConfiguration.class)
@AutoConfigureMockMvc
@ContextConfiguration(initializers = Postgres.Initializer.class)
@Transactional
public abstract class BaseTestSuite {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper().registerModule(new ParameterNamesModule());

    @Autowired
    protected MockMvc mockMvc;
    @Autowired
    protected TaskDAO taskDao;

    public String asJson(Object object) throws Exception {
        return OBJECT_MAPPER.writer().withDefaultPrettyPrinter().writeValueAsString(object);
    }

    public <T> T fromResponse(MvcResult result, Class<T> type) throws Exception {
        return OBJECT_MAPPER.readerFor(type).readValue(result.getResponse().getContentAsString());
    }

    public <T> T fromJson(String json, Class<T> type) throws Exception {
        return OBJECT_MAPPER.readerFor(type).readValue(json);
    }
}
