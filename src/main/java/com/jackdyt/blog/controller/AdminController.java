package com.jackdyt.blog.controller;

import com.jackdyt.blog.dto.PageDTO;
import com.jackdyt.blog.service.CommentService;
import com.jackdyt.blog.service.EssayService;
import com.jackdyt.blog.dto.PageDTO;
import com.jackdyt.blog.service.CommentService;
import com.jackdyt.blog.service.EssayService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AdminController {

    @Autowired
    private EssayService essayService;
    @Autowired
    private CommentService commentService;

    @RequestMapping({"/","admin"})
    public String admin(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search){

        PageDTO pageDTO = essayService.list(search,page,size);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("search", search);
        return "admin";
    }

    @RequestMapping("/admin/delete/{id:\\d*}")
    @RequiresRoles("admin")
    public String adminDeleteEssay(@PathVariable("id") Long id){
        commentService.deleteComment(id);
        essayService.deleteEssay(id);
        return "redirect:/admin";
    }


}
