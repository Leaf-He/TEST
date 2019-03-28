package com.app.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/bashBoard")
public class BashBoardController {

    @RequestMapping("")
     public String index(){
        return  "admin/bashBoard";
     }
}
