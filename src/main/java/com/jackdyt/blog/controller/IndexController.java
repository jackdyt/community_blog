package com.jackdyt.blog.controller;

import com.jackdyt.blog.cache.HotTagCache;
import com.jackdyt.blog.dto.HotTagDTO;
import com.jackdyt.blog.dto.PageDTO;
import com.jackdyt.blog.mapper.UserMapper;
import com.jackdyt.blog.service.EssayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EssayService essayService;

    @GetMapping({"/","index"})
    public String index(HttpServletRequest request, Model model,
                        @RequestParam(name = "page", defaultValue = "1") Integer page,
                        @RequestParam(name = "size", defaultValue = "5") Integer size,
                        @RequestParam(name = "search", required = false) String search,
                        @RequestParam(name = "tag", required = false) String tagName){

        PageDTO pageDTO = essayService.list(tagName,search,page,size);
        model.addAttribute("pageDTO", pageDTO);
        model.addAttribute("search", search);
        List<HotTagDTO> hotTagDTOS = HotTagCache.getHotTagDTOS();
        model.addAttribute("tag", tagName);
        model.addAttribute("hotTagDTOs", hotTagDTOS);
        return "index";
    }
}
