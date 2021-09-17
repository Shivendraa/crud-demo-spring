package com.nighteye.springbootcrud.controller;
import java.util.List;

import com.nighteye.springbootcrud.model.User;
import com.nighteye.springbootcrud.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/users")
    private List<User> getAllUsers()
    {
        return userService.getAllUsers();
    }

    @GetMapping("/users/{userid}")
    private User getUsers(@PathVariable("userid") int userid)
    {
        return userService.getUsersById(userid);
    }

    @DeleteMapping("/users/{userid}")
    private void deleteUser(@PathVariable("userid") int userid)
    {
        userService.delete(userid);
    }

    @PostMapping("/users")
    private int saveUser(@RequestBody User user)
    {
        userService.save(user);
        return user.getUserId();
    }

    @PutMapping("/users/{userid}")
    private User update(@RequestBody User users, @PathVariable("userid") int userid)
    {
        userService.update(users, userid);
        return users;
    }
}
