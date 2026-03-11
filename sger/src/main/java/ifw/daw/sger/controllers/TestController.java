package ifw.daw.sger.controllers;

import java.sql.Connection;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {
    @Autowired
    private DataSource dataSource;
    
    @GetMapping("/test-db")
    @ResponseBody
    public String testConnection() {
        try (Connection conn = dataSource.getConnection()) {
            return "Conexión OK: " + conn.getCatalog();
        } catch (Exception e) {
            return "Error: " + e.getMessage();
        }
    }
}
