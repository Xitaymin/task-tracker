package com.xitaymin.tasktracker.suites;

import com.xitaymin.tasktracker.TaskTrackerApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(TaskTrackerApplication.class)
public class TestSuiteConfiguration {

}
