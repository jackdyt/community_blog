package com.jackdyt.blog.service;

import com.jackdyt.blog.dto.EssayDTO;
import com.jackdyt.blog.dto.PageDTO;
import com.jackdyt.blog.dto.SearchDTO;
import com.jackdyt.blog.exception.CustomizeErrorCode;
import com.jackdyt.blog.exception.CustomizeException;
import com.jackdyt.blog.mapper.EssayMapper;
import com.jackdyt.blog.mapper.EssayMapperExtension;
import com.jackdyt.blog.mapper.UserMapper;
import com.jackdyt.blog.model.Essay;
import com.jackdyt.blog.model.EssayExample;
import com.jackdyt.blog.model.User;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EssayService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private EssayMapper essayMapper;
    @Autowired
    private EssayMapperExtension essayMapperExtension;


    public PageDTO list(String tagName, String search, Integer page, Integer size) {
        if(StringUtils.isNotBlank(search)){
            String[] words = StringUtils.split(search, " ");
            search= String.join("|", words);
        }


        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setSearch(search);
        searchDTO.setTagName(tagName);
        Integer total = essayMapperExtension.countBySearch(searchDTO);
        PageDTO<EssayDTO> pageDTO = new PageDTO();
        pageDTO.setPageInit(total, page, size);

        if (page > pageDTO.getPageNeed()) {
            page = pageDTO.getPageNeed();
        }
        if (page < 1) {
            page = 1;
        }
        Integer offset = size * (page - 1);
        EssayExample essayExample = new EssayExample();
        essayExample.setOrderByClause("gmt_create desc");
        searchDTO.setSize(size);
        searchDTO.setPage(offset);
        List<Essay> essays = essayMapperExtension.selectBySearch(searchDTO);
        List<EssayDTO> essayDTOList = new ArrayList<>();


        for (Essay essay : essays) {
            User user = userMapper.selectByPrimaryKey(essay.getCreator());
            EssayDTO essayDTO = new EssayDTO();
            BeanUtils.copyProperties(essay, essayDTO);
            essayDTO.setUser(user);
            essayDTOList.add(essayDTO);
        }
        pageDTO.setData(essayDTOList);


        return pageDTO;
    }

    public PageDTO list(Long userId, Integer page, Integer size) {
        EssayExample essayExample = new EssayExample();
        essayExample.createCriteria().andCreatorEqualTo(userId);
        Integer total = (int) essayMapper.countByExample(essayExample);
        PageDTO<EssayDTO> pageDTO = new PageDTO();
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
        pageDTO.setData(essayDTOList);


        return pageDTO;
    }

    public EssayDTO getById(Long id) {
        Essay essay = essayMapper.selectByPrimaryKey(id);

        //handle exceptions
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
            essay.setCommentCount(0);
            essay.setViewCount(0);
            essay.setLikeCount(0);
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

    public List<EssayDTO> selectRelated(EssayDTO essayDTO) {
        if(StringUtils.isBlank(essayDTO.getTag())){
            return  new ArrayList<>();
        }
        String[] tags = StringUtils.split(essayDTO.getTag(), ",");
//        String regex = String.join("|", tags);
        Essay essay = new Essay();
        essay.setId(essayDTO.getId());
        essay.setTag(essayDTO.getTag());
        List<Essay> essays = essayMapperExtension.selectRelatedTag(essay);
        //convert list of Essay to list of EssayDTO
        //should use dto we define
        List<EssayDTO> essayDTOS = essays.stream().map(e->{
            EssayDTO essayDTOTmp = new EssayDTO();
            BeanUtils.copyProperties(e, essayDTOTmp);
            return essayDTOTmp;
        }).collect(Collectors.toList());
        return essayDTOS;
    }

    public void deleteEssay(Long id){
        essayMapper.deleteByPrimaryKey(id);
        return;
    }
}
