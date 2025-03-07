package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.models.User;

import java.util.List;

public interface UserService {

    List<User> getAllUsers();
    User getById(Integer id);
    void saveUser(User user);
    void deleteUser(Integer id);
}
