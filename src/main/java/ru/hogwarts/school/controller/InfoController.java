package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
@Profile({"default", "dev", "prod"})
public class InfoController {

    @Value("${server.port}")
    private String serverPort;

    @Value("${info.app.name}")
    private String appName;

    @GetMapping("/port")
    public ResponseEntity<String> getServerPort() {
        return ResponseEntity.ok("The application '" + appName + "' is running on port: " + serverPort);
    }
}
