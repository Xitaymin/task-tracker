package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.dao.entity.Role;
import com.xitaymin.tasktracker.dto.user.UserViewTO;
import com.xitaymin.tasktracker.service.UserService;
import com.xitaymin.tasktracker.web.dto.UserAdminView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/admin")
@Controller
public class WebController {

    private final UserService userService;

    public WebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping()
    public String root() {
        return "redirect:/admin/new-user";
    }

    @PostMapping("/save")
    public String save(@RequestBody UserAdminView userView, Model model) {
        userService.save(userView.toCreateTO());
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
}
