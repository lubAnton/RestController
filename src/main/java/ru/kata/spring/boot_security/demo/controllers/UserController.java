package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RegistrationServiceImpl;
import ru.kata.spring.boot_security.demo.service.UserServiceImpl;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import javax.validation.Valid;

@Controller
public class UserController {
    private final UserServiceImpl userService;
    private final RegistrationServiceImpl registrationService;
    private final UserValidator userValidator;

    @Autowired
    public UserController(UserServiceImpl userService, RegistrationServiceImpl registrationService,
                          UserValidator userValidator) {
        this.userService = userService;
        this.registrationService = registrationService;
        this.userValidator = userValidator;
    }

    @GetMapping("/login")
    public String loginPage () {
        return "login";
    }

    @GetMapping("/registration")
    public String registrationPage(@ModelAttribute("user") User user) {
        return "registration";
    }

    @PostMapping("/registration")
    public String performRegis(@ModelAttribute("user") @Valid User user,
                               BindingResult bindingResult) {
        System.out.println(user.toString());
        if (user.getId()!=0) {
            registrationService.registration(user);
        }
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        registrationService.registration(user);
        return "redirect:/login";
    }
    @GetMapping("/admin/registration")
    public String adminRegis(@ModelAttribute("user") User user){
        return "/admin/adminRegis";
    }

    @PostMapping("/admin/registration")
    public String adminRegisProcess(@ModelAttribute ("user") @Valid User user,
                                    BindingResult bindingResult) {
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            return "admin/adminRegis";
        }
        registrationService.adminRegistration(user);
        return "redirect:/login";
    }

    @GetMapping("/admin")
    public String getUsers(Model model) {
        model.addAttribute("users", userService.getUsers());
        return "users";
    }
    @GetMapping("/user/{id}")
    public String userInfo (@PathVariable ("id") int id, Model model) {
        model.addAttribute("userInfo", userService.getUserInfo(id));
        return "info";
    }

    @GetMapping("/update")
    public String edit (Model model, @RequestParam int id) {
        model.addAttribute("user", userService.getUserInfo(id));
        return "changeUser";
    }
    @PatchMapping ("/update/{id}")
    public String editProcess (@ModelAttribute ("user") User user,
                               @PathVariable ("id") int id) {
        userService.editUser(user);
        return "redirect:/users";
    }

    @DeleteMapping("/deleteUser")
    public String deleteUser (@RequestParam int id) {
        userService.deleteUser(id);
        return "redirect:/users";
    }
}
