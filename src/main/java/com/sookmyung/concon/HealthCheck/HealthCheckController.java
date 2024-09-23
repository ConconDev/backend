package com.sookmyung.concon.HealthCheck;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "헬스 체크")
@RestController
public class HealthCheckController {
    @GetMapping("/")
    public String mainController() {
        return "concon";
    }

    @GetMapping("/api/test")
    public String healthCheck() {
        return "pong";
    }

    @GetMapping("/ping")
    public String ping() {
        return "pong";
    }
}
