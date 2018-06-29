package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author qian
 * @date 2018/6/29
 */
@Controller
public class IndexController {

    @RequestMapping(value = {"/", "/index"})
    public String index() {
        System.err.println("....come on.....index..........");
        return "index";
    }

    @RequestMapping(value = {"/selectAndBinding"})
    public String selectAndBinding() {
        System.err.println("....come on.....selectAndBinding..........");
        return "selectAndBinding";
    }



}
