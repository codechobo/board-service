package com.example.boardservice.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping("/")
    public String indexPageForm() {
        return "index";
    }

    @GetMapping("/members/save")
    public String membersSaveForm() {
        return "members-save";
    }

    @GetMapping("/posts/save")
    public String postsSaveForm() {
        return "posts-save";
    }


}
