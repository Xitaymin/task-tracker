package com.xitaymin.tasktracker.controller;

import com.xitaymin.tasktracker.dao.entity.Role;
import com.xitaymin.tasktracker.dto.user.CreateUserTO;
import com.xitaymin.tasktracker.service.UserService;
import com.xitaymin.tasktracker.web.dto.UserAdminView;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

@Controller
public class WebController {

    private final UserService userService;

    public WebController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/hello")
    public String toHello(Model model) {
        model.addAttribute("message", "This is message from controller.");
        return "addUser";
    }

    @PostMapping("/admin/users")
    public String save(@Valid @RequestBody CreateUserTO userTO, Model model) {

        userService.save(userTO);
        return "addUser";
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

}
