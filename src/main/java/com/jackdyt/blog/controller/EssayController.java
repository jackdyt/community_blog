package com.jackdyt.blog.controller;

import com.jackdyt.blog.dto.CommentDTO;
import com.jackdyt.blog.dto.EssayDTO;
import com.jackdyt.blog.enums.CommentType;
import com.jackdyt.blog.service.CommentService;
import com.jackdyt.blog.service.EssayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class EssayController {
    @Autowired
    private EssayService essayService;
    @Autowired
    private CommentService commentService;
    @GetMapping("/essay/{id}")
    public String essay(@PathVariable(name = "id") Long id, Model model){
        EssayDTO essayDTO = essayService.getById(id);
        List<EssayDTO> relatedEssay = essayService.selectRelated(essayDTO);
        List<CommentDTO> commentDTOs = commentService.listByTargetId(id, CommentType.QUESTION);
        essayService.increaseView(id);
        model.addAttribute("essay", essayDTO);
        model.addAttribute("comments", commentDTOs);
        model.addAttribute("relatedEssays", relatedEssay);
        return "essay";
    }
}
