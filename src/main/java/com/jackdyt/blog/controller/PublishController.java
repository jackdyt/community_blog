package com.jackdyt.blog.controller;

import com.jackdyt.blog.dto.EssayDTO;
import com.jackdyt.blog.model.Essay;
import com.jackdyt.blog.model.User;
import com.jackdyt.blog.service.EssayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }


    @Autowired
    private EssayService essayService;

    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag, HttpServletRequest request,
                            @RequestParam("id") Long id,
                            Model model){

        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error", "Please login");
            return "publish";
        }
        Essay essay = new Essay();
        essay.setTitle(title);
        essay.setDescription(description);
        essay.setCreator(user.getId());
        essay.setTag(tag);
        essay.setId(id);
        essayService.createOrUpdate(essay);

        return "redirect:/index";
    }


    @GetMapping("/publish/{id}")
    public String edit(@PathVariable(name = "id") Long id, Model model){
        EssayDTO essay = essayService.getById(id);
        model.addAttribute("title", essay.getTitle());
        model.addAttribute("description", essay.getDescription());
        model.addAttribute("tag", essay.getTag());
        model.addAttribute("id", id);
        return "publish";

    }
}
