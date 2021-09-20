package com.nighteye.springbootcrud.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nighteye.springbootcrud.model.User;
import com.nighteye.springbootcrud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private ResponseEntity<List<User>> getAllUsers()
    {
        try {
            List<User> users = new ArrayList<>();
            userRepository.findAll().forEach(user -> users.add(user));
            if (users.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{userid}")
    private ResponseEntity<User> getUserById(@PathVariable("userid") int userid)
    {
        Optional<User> user = userRepository.findById(userid);
        if (user.isPresent()) {
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{userid}")
    private ResponseEntity<User> deleteUser(@PathVariable("userid") int userid)
    {
        try {
            if(userRepository.findById(userid).isPresent()) {
                userRepository.deleteById(userid);
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users")
    private ResponseEntity<User> saveUser(@RequestBody User user)
    {
        try {
            User tempUser = userRepository.save(user);
            return new ResponseEntity<>(tempUser, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{userid}")
    private ResponseEntity<User> update(@PathVariable("userid") int userid, @RequestBody User user)
    {
        Optional<User> userOpt = userRepository.findById(userid);
        if(userOpt.isPresent()) {
            User tempUser = userOpt.get();
            tempUser.setUserId(userid);
            tempUser.setUsername(user.getUsername());
            tempUser.setEmail(user.getEmail());
            tempUser.setSalary(user.getSalary());
            userRepository.save(tempUser);
            return new ResponseEntity<>(userRepository.save(tempUser), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
