package com.sookmyung.concon.HealthCheck;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {
    @GetMapping("/api/test")
    public String healthCheck() {
        return "pong";
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
