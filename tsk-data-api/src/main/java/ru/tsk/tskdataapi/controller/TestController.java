package ru.tsk.tskdataapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.tsk.tskdataapi.service.TestService;

@RestController("test")
public class TestController {

    @Autowired
    private TestService testService;

    @GetMapping("test")
    public String dfdfd() {
        return testService.getMessage();
    }
}
