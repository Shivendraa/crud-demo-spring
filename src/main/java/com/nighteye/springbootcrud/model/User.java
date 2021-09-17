package com.nighteye.springbootcrud.model;
import javax.persistence.*;

@Entity
@Table
public class User {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column
    private int userId;
    @Column
    private String username;
    @Column
    private String email;
    @Column
    private int salary;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", salary=" + salary +
                '}';
    }


}