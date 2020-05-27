package com.jackdyt.blog.service;

import com.jackdyt.blog.dto.EssayDTO;
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

    public List<EssayDTO> list() {
        List<Essay> essays = essayMapper.list();
        List<EssayDTO> essayDTOList = new ArrayList<>();
        for (Essay essay: essays){
            User user = userMapper.findById(essay.getCreator());
            EssayDTO essayDTO = new EssayDTO();
            BeanUtils.copyProperties(essay, essayDTO);
            essayDTO.setUser(user);
            essayDTOList.add(essayDTO);
        }
        return essayDTOList;
    }
}
