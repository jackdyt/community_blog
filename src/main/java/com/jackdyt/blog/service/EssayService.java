package com.jackdyt.blog.service;

import com.jackdyt.blog.dto.EssayDTO;
import com.jackdyt.blog.dto.PageDTO;
import com.jackdyt.blog.exception.CustomizeErrorCode;
import com.jackdyt.blog.exception.CustomizeException;
import com.jackdyt.blog.mapper.EssayMapper;
import com.jackdyt.blog.mapper.EssayMapperExtension;
import com.jackdyt.blog.mapper.UserMapper;
import com.jackdyt.blog.model.Essay;
import com.jackdyt.blog.model.EssayExample;
import com.jackdyt.blog.model.User;
import org.apache.ibatis.session.RowBounds;
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
    @Autowired
    private EssayMapperExtension essayMapperExtension;


    public PageDTO list(Integer page, Integer size) {
        Integer total = (int) essayMapper.countByExample(new EssayExample());
        PageDTO pageDTO = new PageDTO();
        pageDTO.setPageInit(total, page, size);

        if (page > pageDTO.getPageNeed()) {
            page = pageDTO.getPageNeed();
        }
        if (page < 1) {
            page = 1;
        }
        Integer offset = size * (page - 1);
        List<Essay> essays = essayMapper.selectByExampleWithRowbounds(new EssayExample(), new RowBounds(offset,size));
        List<EssayDTO> essayDTOList = new ArrayList<>();


        for (Essay essay : essays) {
            User user = userMapper.selectByPrimaryKey(essay.getCreator());
            EssayDTO essayDTO = new EssayDTO();
            BeanUtils.copyProperties(essay, essayDTO);
            essayDTO.setUser(user);
            essayDTOList.add(essayDTO);
        }
        pageDTO.setEssayDTOs(essayDTOList);


        return pageDTO;
    }

    public PageDTO list(Long userId, Integer page, Integer size) {
        EssayExample essayExample = new EssayExample();
        essayExample.createCriteria().andCreatorEqualTo(userId);
        Integer total = (int) essayMapper.countByExample(essayExample);
        PageDTO pageDTO = new PageDTO();
        Integer pageNeed;
        if (total % size == 0) {
            pageNeed = total / size;
        } else {
            pageNeed = total / size + 1;
        }
        if (page < 1) {
            page = 1;
        }
        if (page > pageNeed) {
            page = pageNeed;
        }
        pageDTO.setPageInit(total, page, size);

        Integer offset = size * (page - 1);
        EssayExample example = new EssayExample();
        example.createCriteria().andCreatorEqualTo(userId);
        List<Essay> essays = essayMapper.selectByExampleWithRowbounds(example, new RowBounds(offset,size));

        List<EssayDTO> essayDTOList = new ArrayList<>();


        for (Essay essay : essays) {
            User user = userMapper.selectByPrimaryKey(essay.getCreator());
            EssayDTO essayDTO = new EssayDTO();
            BeanUtils.copyProperties(essay, essayDTO);
            essayDTO.setUser(user);
            essayDTOList.add(essayDTO);
        }
        pageDTO.setEssayDTOs(essayDTOList);


        return pageDTO;
    }

    public EssayDTO getById(Long id) {
        Essay essay = essayMapper.selectByPrimaryKey(id);
        if (essay == null){
            throw new CustomizeException(CustomizeErrorCode.ESSAY_NOT_FOUND);
        }
        EssayDTO essayDTO = new EssayDTO();
        User user = userMapper.selectByPrimaryKey(essay.getCreator());
        BeanUtils.copyProperties(essay, essayDTO);
        essayDTO.setUser(user);
        return essayDTO;
    }

    public void createOrUpdate(Essay essay) {
        if (essay.getId() == null) {
            essay.setGmtCreate(System.currentTimeMillis());
            essay.setGmtModified(essay.getGmtCreate());
            essayMapper.insert(essay);
        } else {

            Essay updateEssay = new Essay();
            updateEssay.setGmtModified(System.currentTimeMillis());
            updateEssay.setTitle(essay.getTitle());
            updateEssay.setDescription(essay.getDescription());
            updateEssay.setTag(essay.getTag());
            EssayExample example = new EssayExample();
            example.createCriteria().andIdEqualTo(essay.getId());
            int updateRes = essayMapper.updateByExampleSelective(updateEssay, example);
            if (updateRes!=1){
                throw new CustomizeException(CustomizeErrorCode.ESSAY_NOT_FOUND);
            }
        }
    }

    public void increaseView(Long id) {
        Essay essay = new Essay();
        essay.setId(id);
        essay.setViewCount(1);
        essayMapperExtension.incView(essay);
    }

}
