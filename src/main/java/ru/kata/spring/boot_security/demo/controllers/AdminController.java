package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.Role;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;

@Controller
public class AdminController {
    private final UserService userService;
    private final UserValidator userValidator;

    @Autowired
    public AdminController(UserService userService,
                           UserValidator userValidator) {
        this.userService = userService;
        this.userValidator = userValidator;
    }

    @GetMapping("/login")
    public String loginPage () {
        return "login";
    }


    @GetMapping("/admin")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }

    @GetMapping("/admin/registration")
    public String adminRegis(@ModelAttribute("user") User user, Model model){
        model.addAttribute("roles", userService.getRoles());
        return "registration";
    }

    @PostMapping("/admin/registration")
    public String adminRegisProcess(@ModelAttribute ("user") @Valid User user,
                                    BindingResult bindingResult, Model model) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.saveUser(user);
        return "redirect:/login";
    }
    @GetMapping("/admin/users/{id}")
    public String userInfo (@PathVariable ("id") int id, Model model) {
        model.addAttribute("user", userService.getUserInfo(id));
        return "info";
    }

    @GetMapping("/admin/update")
    public String edit (Model model, @RequestParam int id) {
        model.addAttribute("user", userService.getUserInfo(id));
        model.addAttribute("roles", userService.getRoles());
        return "changeUser";
    }
    @PostMapping ("admin/update/save")
    public String editProcess (@ModelAttribute ("user") User user){

        userService.editUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/deleteUser")
    public String deleteUser (@RequestParam int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }
}
