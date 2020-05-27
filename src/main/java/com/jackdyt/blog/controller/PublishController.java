package com.jackdyt.blog.controller;

import com.jackdyt.blog.mapper.EssayMapper;
import com.jackdyt.blog.mapper.UserMapper;
import com.jackdyt.blog.model.Essay;
import com.jackdyt.blog.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

@Controller
public class PublishController {
    @GetMapping("/publish")
    public String publish(){
        return "publish";
    }

    @Autowired
    private EssayMapper essayMapper;
    @Autowired
    private UserMapper userMapper;
    @PostMapping("/publish")
    public String doPublish(@RequestParam("title") String title,
                            @RequestParam("description") String description,
                            @RequestParam("tag") String tag, HttpServletRequest request, Model model){
        Cookie[] cookies = request.getCookies();
        User user = null;
        if (cookies != null && cookies.length != 0){
            for (Cookie cookie:cookies){
                if (cookie.getName().equals("token")){
                    String token = cookie.getValue();
                    user =  userMapper.findByToken(token);
                    if (user != null){
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        if (user==null){
            model.addAttribute("error", "Please login");
            return "publish";
        }
        Essay essay = new Essay();
        essay.setTitle(title);
        essay.setDescription(description);
        essay.setCreator(user.getId());
        essay.setGmtCreate(System.currentTimeMillis());
        essay.setGmtModified(essay.getGmtCreate());
        essay.setTag(tag);
        essayMapper.create(essay);

        return "redirect:/index";
    }
}
