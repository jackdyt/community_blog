package com.jackdyt.blog.mapper;

import com.jackdyt.blog.dto.SearchDTO;
import com.jackdyt.blog.model.Essay;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EssayMapperExtension {
    int incView(Essay record);
    int incComment(Essay record);
    List<Essay> selectRelatedTag(Essay essay);
    Integer countBySearch(SearchDTO searchDTO);
    List<Essay> selectBySearch(SearchDTO searchDTO);
}