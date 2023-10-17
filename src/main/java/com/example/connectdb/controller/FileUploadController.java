package com.example.connectdb.controller;

import com.example.connectdb.service.ConnectionDBService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;

@Controller
public class FileUploadController {

    @Autowired
    ConnectionDBService connectionDB;

    @Autowired
    HttpSession session;

    @GetMapping("/uploadPage")
    public String uploadPage() {
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
            System.out.println("Roles: " + str + " | " + session.getId());
            return "home";
        }
        return "upload"; // Повертаємо сторінку для завантаження файлу
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("excelFile") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // Зберігаємо Excel файл на сервері
                String fileName = file.getOriginalFilename();
                String filePath = "D:/uploads/"; // Шлях до директорії, де буде збережений файл
                file.transferTo(new File(filePath + fileName));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "redirect:/uploadPage"; // Після завантаження повертаємо користувача на сторінку завантаження
    }
}

