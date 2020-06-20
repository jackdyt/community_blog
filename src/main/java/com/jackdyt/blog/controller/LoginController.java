package com.jackdyt.blog.controller;

import com.jackdyt.blog.model.Admin;
import com.jackdyt.blog.service.AdminService;
import com.jackdyt.blog.service.AdminService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {
    @Autowired
    private AdminService adminService;

    @RequestMapping("/loginview")
    public String loginView(){
        return "login";
    }

    @RequestMapping("/registerview")
    public String register(){
        return "register";
    }

    @RequestMapping("/register")
    public String register(Admin admin) {
        try {
            adminService.register(admin.getUserName(), admin.getPassword());
            return "redirect:/loginview";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/registerview";
        }
    }

    @RequestMapping("/login")
    public String login(@RequestParam("userName") String username,@RequestParam("password") String password, Model model){
        Subject subject = SecurityUtils.getSubject();
        try{
            subject.login(new UsernamePasswordToken(username, password));
            return "redirect:/admin";
        }catch (UnknownAccountException e) {
            e.printStackTrace();
            model.addAttribute("message","wrong username!");
            System.out.println("wrong username!");
        } catch (IncorrectCredentialsException e) {
            e.printStackTrace();
            System.out.println("wrong password!");
            model.addAttribute("message","wrong password!");
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
        return "login";

    }
}
