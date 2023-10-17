package com.example.connectdb.controller;

import com.example.connectdb.service.ConnectionDBService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.sql.Connection;
import java.sql.ResultSet;

@Controller
public class LoginController {

    @Autowired
    private ConnectionDBService connectionDB;

    @Resource
    HttpSession session;

    @GetMapping("/login1")
    public String showLoginPage() {
        System.out.println("GET!!!");
        return "login";
    }

    @PostMapping("/login")
    public String authenticate(@RequestParam String username, @RequestParam String password) {
        System.out.println("POST!!!");
        Connection conn = connectionDB.connectToDB(username, password);

        if (conn != null) {
            session.setAttribute("username", conn);
            return "home";
        } else {
            return "login";
        }
    }

    @GetMapping("/other")
    public String otherPoint() {
        Connection conn = (Connection) session.getAttribute("username");

        if (conn != null) {
            ResultSet resultSet = null;
            try {
                resultSet = connectionDB.query("select * from userRoles(null)", conn);
            } catch (Exception e) {
                System.out.println("Failed to retrieve data!");
            }
            String str = "";
            try {
                if (resultSet != null) {
                    while (resultSet.next()) {
                        str = str + resultSet.getString("role") + " ";
                    }
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Roles: " + str);
            return "home";
        } else {
            return "login";
        }
    }
}