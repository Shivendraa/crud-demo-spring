package com.nighteye.springbootcrud.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nighteye.springbootcrud.model.User;
import com.nighteye.springbootcrud.repository.UserRepository;
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
    private UserRepository userRepository;

    @GetMapping("/users")
    private List<User> getAllUsers()
    {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(user));
        if(users.isEmpty()){
            return users;
        }
        return users;
    }

    @GetMapping("/users/{userid}")
    private User getUsers(@PathVariable("userid") int userid)
    {
        return userRepository.findById(userid).get();
    }

    @DeleteMapping("/users/{userid}")
    private void deleteUser(@PathVariable("userid") int userid)
    {
        userRepository.deleteById(userid);
    }

    @PostMapping("/users")
    private String saveUser(@RequestBody User user)
    {
        userRepository.save(user);
        return user.toString();
    }

    @PutMapping("/users/{userid}")
    private String update(@PathVariable("userid") int userid, @RequestBody User user)
    {
        Optional<User> userOpt = userRepository.findById(userid);
        if(userOpt.isPresent()) {
            User tempUser = userOpt.get();
            tempUser.setUserId(userid);
            tempUser.setUsername(user.getUsername());
            tempUser.setEmail(user.getEmail());
            tempUser.setSalary(user.getSalary());
            userRepository.save(tempUser);
            return tempUser.toString();
        }
        else
            return "No user with given id found!";
    }
}
