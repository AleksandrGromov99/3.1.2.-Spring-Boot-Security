package ru.kata.spring.boot_security.demo.dao;


import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserDao {

    public List<User> getUserList();

    public User getUser(long id);

    public void saveUser(User user);

    public void deleteUser(long id);

    public void updateUser(User user);
}

