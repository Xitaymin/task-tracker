package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.dto.task.CreateTaskTO;
import com.xitaymin.tasktracker.dto.task.EditTaskTO;
import com.xitaymin.tasktracker.dto.task.TaskViewTO;
import com.xitaymin.tasktracker.service.TaskService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Collection;

//Создание задачи.
//Задача может быть создана только в рамках проекта
//Assignee - может быть не указан при создании задачи, но если указан - должен быть в проекте, которому принадлежит эта задача
//Название и описание обязательны
//reporter - любой пользователь, обязателен
//Задачи удалять нельзя
//Редактирование. Редактировать можно только title, description и assignee.
//Привязка задачи к пользователю - устанавливаем assignee. Assignee должен быть в проекте, которому принадлежит эта задача.
@RestController
@RequestMapping(TaskController.TASKS)
public class TaskController {

    private final TaskService taskService;
    public static final String TASKS = "/tasks";

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping()
    public TaskViewTO createTask(@Valid @RequestBody CreateTaskTO taskTO) {
        return taskService.saveTask(taskTO);
    }

    @GetMapping("/{id}")
    public TaskViewTO getTask(@PathVariable long id) {
        return taskService.getTask(id);
    }

    @GetMapping()
    public Collection<TaskViewTO> getTasks() {
        return taskService.getTasks();
    }

    @PutMapping()
    public void editTask(@Valid @RequestBody EditTaskTO taskTO) {
        taskService.editTask(taskTO);
    }
}
