package com.nighteye.springbootcrud.service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import com.nighteye.springbootcrud.model.User;
import com.nighteye.springbootcrud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAllUsers()
    {
        List<User> users = new ArrayList<>();
        userRepository.findAll().forEach(user -> users.add(user));
        return users;
    }
    public User getUsersById(int id)
    {
        return userRepository.findById(id).get();
    }
    public void save(User user)
    {
        userRepository.save(user);
    }

    public void delete(int id)
    {
        userRepository.deleteById(id);
    }
    //updating a record
    public void update(User user, int userid)
    {
        Optional<User> userOpt = userRepository.findById(userid);
        if(userOpt.isPresent()) {
            User tempUser = userOpt.get();
            tempUser.setUserId(userid);
            tempUser.setUsername(user.getUsername());
            tempUser.setEmail(user.getEmail());
            tempUser.setSalary(user.getSalary());
            userRepository.save(tempUser);
        }
    }

}
