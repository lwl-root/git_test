package com.lwl.controller;

import com.lwl.entity.User;
import com.lwl.service.UserService;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserControllerTest {

    @Test
    void getAllModels() {
        UserController userController = new UserController();
        List<User> allUser = userController.getAllUser();
        allUser.forEach(System.out::println);
    }
}