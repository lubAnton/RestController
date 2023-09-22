package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.DTO.UserDTO;
import ru.kata.spring.boot_security.demo.entity.User;
import ru.kata.spring.boot_security.demo.service.RoleService;
import ru.kata.spring.boot_security.demo.service.UserService;
import ru.kata.spring.boot_security.demo.util.UserValidator;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/rest")
@CrossOrigin
public class AdminRestController {
    private final UserService userService;
    private final UserValidator userValidator;
    private final RoleService roleService;

    public AdminRestController(UserService userService, UserValidator userValidator, RoleService roleService) {
        this.userService = userService;
        this.userValidator = userValidator;
        this.roleService = roleService;
    }

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = userService.getUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/{id}")
    public ResponseEntity<User>  getUser(@PathVariable("id") int id) {
        User user = userService.getUserInfo(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }
    @PostMapping
    public ResponseEntity<HttpStatus> saveUser(@RequestBody User user, BindingResult bindingResult) {
//        userValidator.validate(userDTO, bindingResult);
//        if (bindingResult.hasErrors()) {
//            System.out.println("нужно обработать ошибку");
//        }
        userService.saveUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<HttpStatus> updateUser(@RequestBody User user, BindingResult bindingResult,
                                                 @PathVariable("id") int id) {
        //userValidator.validate(user, bindingResult);
//        if (bindingResult.hasErrors()) {
//            System.out.println("нужно обработать ошибку");
//        }
        userService.editUser(user);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") int id){
        userService.deleteUser(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
    @GetMapping(path = "/api/auth", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getAuthUser(@CurrentSecurityContext(expression = "authentication") Principal principal) {
        User user = userService.findUserByName(principal.getName());
        return ResponseEntity.ok(user);
    }

}
