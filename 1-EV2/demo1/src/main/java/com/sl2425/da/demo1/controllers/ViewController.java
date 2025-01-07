package com.sl2425.da.demo1.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ViewController
{
    @GetMapping("/")
    public String index()
    {
        return "index";
    }
}
