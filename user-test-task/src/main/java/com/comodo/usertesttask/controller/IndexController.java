package com.comodo.usertesttask.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import java.util.Map;

@Controller
@RequestMapping(value = { "/", "/index**", "/welcome**"}, method = RequestMethod.GET)
public class IndexController {

    @RequestMapping(value = "/", method = RequestMethod.GET)
    public String hello(Map<String, Object> model){
        model.put("message", "Тестовое задание");
        return "index";
    }

}
