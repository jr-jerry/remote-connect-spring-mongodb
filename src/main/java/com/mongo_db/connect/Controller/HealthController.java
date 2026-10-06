package com.mongo_db.connect.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/test")
public class HealthController {
    @GetMapping
    public Map<String,String> getEndpoint(){
        return Map.of("status","application working ");
    }
}
