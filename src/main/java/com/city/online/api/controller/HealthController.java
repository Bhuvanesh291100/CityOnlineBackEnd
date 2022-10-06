package com.city.online.api.controller;

import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/health")
public class HealthController {

    @GetMapping("")
    @Parameter(in = ParameterIn.HEADER, name = "Authorization", required = true)
    public String getHealth() {

        return "Success";
    }
}
