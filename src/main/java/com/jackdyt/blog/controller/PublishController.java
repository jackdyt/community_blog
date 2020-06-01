package com.jackdyt.blog.controller;

import com.jackdyt.blog.cache.TagCache;
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
    public String publish(Model model){
        model.addAttribute("tags", TagCache.get());
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
        model.addAttribute("tags", TagCache.get());
        User user = (User) request.getSession().getAttribute("user");
        if (user==null){
            model.addAttribute("error", "Please login");
            return "publish";
        }
        if (!TagCache.validTag(tag)){
            model.addAttribute("error", "Invalid tag. Please choose from the list.");
            return "publish";
        }

        Essay essay = new Essay();
        essay.setTitle(title);
        essay.setDescription(description);
        essay.setCreator(user.getId());
        essay.setTag(tag);
        essay.setId(id);
        essay.setViewCount(0);
        essay.setCommentCount(0);
        essay.setLikeCount(0);
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
        model.addAttribute("tags", TagCache.get());

        return "publish";

    }
}
