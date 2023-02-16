package com.xitaymin.tasktracker.service.impl;

import com.xitaymin.tasktracker.dao.TaskDAO;
import com.xitaymin.tasktracker.dao.entity.Task;
import com.xitaymin.tasktracker.dao.entity.User;
import com.xitaymin.tasktracker.dto.task.CreateTaskTO;
import com.xitaymin.tasktracker.dto.task.EditTaskTO;
import com.xitaymin.tasktracker.dto.task.TaskViewTO;
import com.xitaymin.tasktracker.service.TaskService;
import com.xitaymin.tasktracker.service.validators.TaskValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static com.xitaymin.tasktracker.service.utils.EntityAbsentUtils.throwExceptionIfAbsent;
import static com.xitaymin.tasktracker.service.validators.impl.TaskValidatorImpl.TASK_NOT_FOUND;
import static java.util.stream.Collectors.toSet;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {

    private final TaskDAO taskDAO;
    private final TaskValidator taskValidator;

    @Override
    @Transactional
    public void editTask(@Valid EditTaskTO taskTO) {
        Task task = taskDAO.findOne(taskTO.getId());

        throwExceptionIfAbsent(TASK_NOT_FOUND, task, taskTO.getId());

        task.setTitle(taskTO.getTitle());
        task.setDescription(taskTO.getDescription());
    }

    @Override
    public TaskViewTO getTask(long id) {
        Task task = taskDAO.findFullTaskById(id);

        throwExceptionIfAbsent(TASK_NOT_FOUND, task, id);

        return convertToTO(task);
    }

    @Override
    public Collection<TaskViewTO> getTasks() {

        return taskDAO.findAllFullTasks()
                .stream()
                .map(this::convertToTO)
                .collect(toSet());
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
        Set<Long> subtasksId = new HashSet<>();

        Set<Task> subtasks = savedTask.getChildTasks();
        if (!subtasks.isEmpty()) {
            for (Task task : subtasks) {
                subtasksId.add(task.getId());
            }
        }

        Long parentId = null;
        Task parent = savedTask.getParent();
        if (parent != null) {
            parentId = parent.getId();
        }
        return new TaskViewTO(savedTask.getId(),
                savedTask.getTitle(),
                savedTask.getDescription(),
                savedTask.getReporter().getId(),
                savedTask.getAssignee().getId(),
                savedTask.getProject().getId(),
                savedTask.getType(),
                subtasksId,
                parentId);
    }
}