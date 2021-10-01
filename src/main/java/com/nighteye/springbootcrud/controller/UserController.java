package com.nighteye.springbootcrud.controller;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nighteye.springbootcrud.model.User;
import com.nighteye.springbootcrud.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserRepository userRepository;

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping("/users")
    private ResponseEntity<List<User>> getAllUsers()
    {
        logger.debug("Get Request called");
        try {
            List<User> users = new ArrayList<>();
            userRepository.findAll().forEach(user -> users.add(user));
            if (users.isEmpty()) {
                logger.warn("No users found");
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            logger.info("Get request success");
            return new ResponseEntity<>(users, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("something went wrong");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/users/{userid}")
    private ResponseEntity<User> getUserById(@PathVariable("userid") int userid)
    {
        logger.debug("Get Request for individual called");
        Optional<User> user = userRepository.findById(userid);
        if (user.isPresent()) {
            logger.info("Get request success");
            return new ResponseEntity<>(user.get(), HttpStatus.OK);
        }
        else {
            logger.warn("No user found with requested id");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/users/{userid}")
    private ResponseEntity<User> deleteUser(@PathVariable("userid") int userid)
    {
        logger.debug("delete Request called");
        try {
            if(userRepository.findById(userid).isPresent()) {
                userRepository.deleteById(userid);
                logger.info("delete request success");
                return new ResponseEntity<>(HttpStatus.OK);
            }
            else {
                logger.warn("No user found with requested id");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            logger.error("something went wrong");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/users")
    private ResponseEntity<User> saveUser(@RequestBody User user)
    {
        logger.debug("Post Request called");
        try {
            User tempUser = userRepository.save(user);
            logger.info("post request success");
            return new ResponseEntity<>(tempUser, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("something went wrong");
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/users/{userid}")
    private ResponseEntity<User> update(@PathVariable("userid") int userid, @RequestBody User user)
    {
        logger.debug("put Request called");
        Optional<User> userOpt = userRepository.findById(userid);
        if(userOpt.isPresent()) {
            User tempUser = userOpt.get();
            tempUser.setUserId(userid);
            tempUser.setUsername(user.getUsername());
            tempUser.setEmail(user.getEmail());
            tempUser.setSalary(user.getSalary());
            userRepository.save(tempUser);
            logger.info("put request success");
            return new ResponseEntity<>(userRepository.save(tempUser), HttpStatus.OK);
        } else {
            logger.warn("No user found with requested id");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
