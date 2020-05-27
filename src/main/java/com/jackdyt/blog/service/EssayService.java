package com.jackdyt.blog.service;

import com.jackdyt.blog.dto.EssayDTO;
import com.jackdyt.blog.dto.PageDTO;
import com.jackdyt.blog.mapper.EssayMapper;
import com.jackdyt.blog.mapper.UserMapper;
import com.jackdyt.blog.model.Essay;
import com.jackdyt.blog.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EssayService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EssayMapper essayMapper;

    public PageDTO list(Integer page, Integer size) {
        Integer total = essayMapper.count();
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPageInit(total, page, size);

        if (page > pageDTO.getPageNeed()){
            page = pageDTO.getPageNeed();
        }
        if (page < 1){
            page = 1;
        }
        Integer offset = size*(page-1);
        List<Essay> essays = essayMapper.list(offset,size);
        List<EssayDTO> essayDTOList = new ArrayList<>();


        for (Essay essay: essays){
            User user = userMapper.findById(essay.getCreator());
            EssayDTO essayDTO = new EssayDTO();
            BeanUtils.copyProperties(essay, essayDTO);
            essayDTO.setUser(user);
            essayDTOList.add(essayDTO);
        }
        pageDTO.setEssayDTOs(essayDTOList);


        return pageDTO;
    }
}
