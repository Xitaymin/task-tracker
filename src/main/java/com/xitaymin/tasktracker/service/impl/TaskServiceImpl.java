package com.xitaymin.tasktracker.service.impl;

import com.xitaymin.tasktracker.dao.ProjectDao;
import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.task.CreateTaskTO;
import com.xitaymin.tasktracker.dto.task.EditTaskTO;
import com.xitaymin.tasktracker.dto.task.TaskViewTO;
import com.xitaymin.tasktracker.service.TaskService;
import com.xitaymin.tasktracker.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.service.validators.TaskValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;

import static com.xitaymin.tasktracker.service.validators.impl.TaskValidatorImpl.TASK_NOT_FOUND;

//Создание задачи.
//Задача может быть создана только в рамках существующего проекта
//Assignee - может быть не указан при создании задачи, но если указан, он должен быть в группе привязанной к проекту задачи и не должен быть удаленным
//Название и описание обязательны
//reporter - любой пользователь
//Задачи удалять нельзя
//Редактирование. Редактировать можно только title и description. project и reporter менять нельзя. Assignee меняется только соответствующей операцией
//Привязка задачи к пользователю - устанавливаем assignee. Assignee должен быть в одной из групп привязанных к проекту, которому принадлежит эта задача. .
//Создание дочерних тасок:
//Нельзя создавать таски с типом SUBTASK без родительской задачи.
//2 иерархии EPIC -> STORY -> ISSUE -> SUBTASK и EPIC -> BUG
//Каждый из типов тасок может содержать дочерние задачи только следующего уровня.
@Service
public class TaskServiceImpl implements TaskService {

    private final TaskDAO taskDAO;
    private final TaskValidator taskValidator;
    private final ProjectDao projectDao;
    private final UserDAO userDao;


    public TaskServiceImpl(TaskDAO taskDAO, TaskValidator taskValidator, ProjectDao projectDao, UserDAO userDao) {
        this.taskDAO = taskDAO;
        this.taskValidator = taskValidator;
        this.projectDao = projectDao;
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void editTask(@Valid EditTaskTO taskTO) {
        Task task = taskDAO.findOne(taskTO.getId());
        if (task == null) {
            throw new NotFoundResourceException(String.format(TASK_NOT_FOUND, taskTO.getId()));
        }
        task.setTitle(taskTO.getTitle());
        task.setDescription(taskTO.getDescription());
        taskDAO.update(task);
    }

    @Override
    public TaskViewTO getTask(long id) {
        Task task = taskDAO.findOne(id);
        if (task == null) {
            throw new NotFoundResourceException(String.format(TASK_NOT_FOUND, id));
        } else {
            return convertToTO(task);
        }
    }

    @Override
    public Collection<TaskViewTO> getTasks() {
        Collection<TaskViewTO> results = new HashSet<>();
        for (Task task : taskDAO.findAll()) {
            TaskViewTO to = convertToTO(task);
            results.add(to);
        }
        return results;
    }

    @Override
    @Transactional
    public TaskViewTO saveTask(CreateTaskTO taskTO) {
        Task task = taskValidator.getTaskValidForSave(taskTO);
        User assignee = task.getAssignee();
        assignee.getTasks().add(task);

        Task parentTask = task.getParent();
        if (parentTask != null) {
            parentTask.getChildTasks().add(task);
        }

        Task savedTask = taskDAO.save(task);
        return convertToTO(savedTask);
    }

    private TaskViewTO convertToTO(Task savedTask) {
        return new TaskViewTO(savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getReporter().getId(),
                savedTask.getAssignee().getId(),
                savedTask.getProject().getId(),
                savedTask.getType());
    }
}