package com.jackdyt.blog.controller;

import com.jackdyt.blog.dto.PageDTO;
import com.jackdyt.blog.model.User;
import com.jackdyt.blog.service.EssayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;

@Controller

public class ProfileController {

    @Autowired
    private EssayService essayService;

    @GetMapping("/profile/{action}")
    public String profile(@PathVariable(name = "action") String action, Model model,
                          HttpServletRequest request,
                          @RequestParam(name = "page", defaultValue = "1") Integer page,
                          @RequestParam(name = "size", defaultValue = "5") Integer size){

        User user = (User) request.getSession().getAttribute("user");
        if (user == null){
            return "redirect:index";
        }
        if (action.equals("posts")){
            model.addAttribute("section", "posts");
            model.addAttribute("sectionName", "My posts");
        }else if(action.equals("replies")){
            model.addAttribute("section", "replies");
            model.addAttribute("sectionName", "Latest Reply");
        }
        PageDTO pageDTO =  essayService.list(user.getId(), page, size);
        model.addAttribute("pageDTO", pageDTO);
        return "profile";
    }
}
