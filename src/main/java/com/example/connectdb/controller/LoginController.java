package com.example.connectdb.controller;

import com.example.connectdb.service.ConnectionDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

@Controller
public class LoginController {

    @Autowired
    ConnectionDBService connectionDB;

    Connection connection;

    @GetMapping("/login1")
    public String showLoginPage(){

        System.out.println("smth write!!!");
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String username, @RequestParam String password){
        connection = connectionDB.connectToDB(username, password);
        ResultSet resultSet = null;
        try {
            resultSet = connectionDB.query("select name = system_user", connection);
        }catch (Exception e){
            System.out.println("Не вдалося отримати дані!");
        }
        String str = "";
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    str = str + ", " + resultSet.getString("name");
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("login: " + username + " password: " + password + " SystemUser: " + str);
        return "home";
    }

    @GetMapping("/other")
    public String otherPoint(){

        ResultSet resultSet = null;
        try {
            resultSet = connectionDB.query("select name = system_user", connection);
        }catch (Exception e){
            System.out.println("Не вдалося отримати дані!");
        }
        String str = "";
        try {
            if (resultSet != null) {
                while (resultSet.next()) {
                    str = str + ", " + resultSet.getString("name");
                }
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        System.out.println("SystemUser: " + str);
        return "home";
    }

}
