package com.xitaymin.tasktracker.model.service.impl;

import com.xitaymin.tasktracker.dao.ProjectDao;
import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.UserDAO;
import com.xitaymin.tasktracker.dao.entity.Project;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.model.dto.task.CreateTaskTO;
import com.xitaymin.tasktracker.model.dto.task.TaskViewTO;
import com.xitaymin.tasktracker.model.service.TaskService;
import com.xitaymin.tasktracker.model.service.exceptions.NotFoundResourceException;
import com.xitaymin.tasktracker.model.service.validators.TaskValidator;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Optional;

import static com.xitaymin.tasktracker.model.service.validators.impl.TaskValidatorImpl.PROJECT_DOESNT_EXIST;
import static com.xitaymin.tasktracker.model.service.validators.impl.TaskValidatorImpl.TASK_NOT_FOUND;

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

    //todo remove model package

    @Override
    @Transactional
    public void editTask(Task task) {
        taskValidator.validateForUpdate(task);
        taskDAO.update(task);
    }

    @Override
    public Task getTask(long id) {
        Task task = taskDAO.findOne(id);
        if (task == null) {
            throw new NotFoundResourceException(String.format(TASK_NOT_FOUND, id));
        } else {
            return task;
        }
    }

    @Override
    public Collection<Task> getTasks() {
        return taskDAO.findAll();
    }

    @Override
    @Transactional
    public TaskViewTO saveTask(CreateTaskTO taskTO) {
        //todo fix return type of validateForSave
        Task task = fillTaskForSave(taskTO);
        Project project = taskValidator.validateForSave(taskTO);
//        Task task = taskTO.convertToEntity();
        task.setProject(project);
        Task savedTask = taskDAO.save(task);
        return convertToTO(savedTask);
    }

    private Task fillTaskForSave(CreateTaskTO taskTO) {
        Optional<Project> optionalProject = Optional.ofNullable(projectDao.findByIdWithTeams(taskTO.getProjectId()));
        Project project = optionalProject.orElseThrow(() -> new NotFoundResourceException(String.format(
                PROJECT_DOESNT_EXIST,
                taskTO.getProjectId())));
        User reporter = userDao.findOne(taskTO.getReporter());
        User assignee = null;
        if (taskTO.getAssignee() != null) {
            assignee = userDao.findOne(taskTO.getAssignee());
        }


        Task task = new Task();
        task.setTitle(taskTO.getTitle());
        task.setDescription(taskTO.getDescription());
        task.setType(taskTO.getType());
        task.setProject(project);
        task.setReporter(reporter);
        task.setAssignee(assignee);
        return task;
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