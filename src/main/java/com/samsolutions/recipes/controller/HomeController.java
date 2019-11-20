package com.samsolutions.recipes.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author kaminskiy.alexey
 * @since 2019.11
 */
@Controller
public class HomeController {
    @RequestMapping("/")
    public String startPage() {
        return "index";
    }

}
