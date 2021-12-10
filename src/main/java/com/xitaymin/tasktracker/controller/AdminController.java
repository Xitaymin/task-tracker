package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.dao.ProjectDao;
import com.xitaymin.tasktracker.dao.entity.Role;
import com.xitaymin.tasktracker.dao.entity.TaskType;
import com.xitaymin.tasktracker.dto.user.FullUserTO;
import com.xitaymin.tasktracker.dto.user.UserViewTO;
import com.xitaymin.tasktracker.service.UserService;
import com.xitaymin.tasktracker.web.dto.AdminTaskView;
import com.xitaymin.tasktracker.web.dto.UserAdminView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/admin")
@Controller
public class AdminController {

    private final UserService userService;
    private final ProjectDao projectDao;

    public AdminController(UserService userService, ProjectDao projectDao) {
        this.userService = userService;
        this.projectDao = projectDao;
    }

    @GetMapping()
    public String root() {
        return "redirect:/admin/new-user";
    }

    @PostMapping("/save")
    public String save(@Valid @ModelAttribute UserAdminView userView) {
        if (userView.getId() == null) {
            userService.save(userView.toCreateTO());
        } else {
            userService.editUser(userView.toEditTO());
        }
        return "redirect:/admin/users";
    }

    @GetMapping("/new-user")
    public String newUserForm(Model model) {
//        EnumSet<Role> availableRoles = EnumSet.of(Role.DEVELOPER,Role.MANAGER,Role.ADMIN);
        Role[] availableRoles = Role.values();
        model.addAttribute("user", new UserAdminView());
//        model.addAttribute("type", type.toUpperCase());
        model.addAttribute("availableRoles", availableRoles);
        model.addAttribute("create", true);
        return "addUser";
    }

    @GetMapping("/users")
    public String users(Model model) {
        Collection<UserViewTO> users = userService.getAllUsers();
        List<UserAdminView> views = users.stream().map(UserAdminView::of).collect(Collectors.toList());
        model.addAttribute("users", views);
        return "users";
    }

    @GetMapping("users/delete/{id}")
    public String deleteUser(@PathVariable long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users";
    }

    @GetMapping("users/edit/{id}")
    public String editUser(@PathVariable long id, Model model) {
        FullUserTO fullUserTO = userService.getById(id);
        UserAdminView userAdminView = UserAdminView.of(fullUserTO);
        model.addAttribute("user", userAdminView);
        model.addAttribute("create", false);

        Role[] availableRoles = Role.values();
        model.addAttribute("availableRoles", availableRoles);

        return "addUser";
    }

    @GetMapping("/new-task")
    public String fillTask(Model model) {
        model.addAttribute("create", true);
        model.addAttribute("task", new AdminTaskView());
        TaskType[] taskTypes = TaskType.values();
        model.addAttribute("taskTypes", taskTypes);
//        List<Project> projects = projectDao.findAll();
//        model.addAttribute(projects);
        return "task";
    }

}
