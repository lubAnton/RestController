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
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;
import java.security.Principal;

@Controller
public class AdminController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final RoleService roleService;


    @Autowired
    public AdminController(UserService userService,
                           UserValidator userValidator, RoleService roleService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleService = roleService;
    }

    @GetMapping("/login")
    public String loginPage () {
        return "login";
    }

    @GetMapping("/admin/registration")
    public String adminRegis(@ModelAttribute("user") User user, Model model){
        model.addAttribute("roles", roleService.getRoles());
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
        return "redirect:/admin";
    }
    @PatchMapping("admin/update/save")
    public String editProcess (@ModelAttribute ("user") User user){
        System.out.println(72);
        userService.editUser(user);
        return "redirect:/admin";
    }

    @DeleteMapping("/admin/deleteUser")
    public String deleteUser (@RequestParam int id) {
        userService.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/admin")
    public String adminPage (Model model, @ModelAttribute ("roll") Role role,
                                  @ModelAttribute("us") User user, Principal principal) {
        model.addAttribute("users", userService.getUsers());
        model.addAttribute("roles", roleService.getRoles());

        model.addAttribute("userPrinc", userService.findUserByName(principal.getName()));
        return "admin_Page";
    }
}
