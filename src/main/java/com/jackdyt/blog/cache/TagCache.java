package com.jackdyt.blog.cache;

import com.jackdyt.blog.dto.TagDTO;
import org.apache.commons.lang3.StringUtils;
import java.util.*;

public class TagCache {

    public static List<TagDTO> get(){
        List<TagDTO> tagDTOS = new ArrayList<>();
        TagDTO language= new TagDTO();
        language.setCategoryName("Programming Language");
        language.setTags(Arrays.asList("Spring Boot", "Spring"));
        tagDTOS.add(language);

        TagDTO dataScience = new TagDTO();
        dataScience.setCategoryName("Data Science");
        dataScience.setTags(Arrays.asList("Data Analysis", "Machine Learning"));
        tagDTOS.add(dataScience);
        return tagDTOS;

    }

    public static boolean validTag(String tag){
        String[] tags = StringUtils.split(tag,",");
        List<TagDTO> tagDTOList = get();
        Set<String> validTags = new HashSet<>();
        for (TagDTO tagDTO: tagDTOList){
            for (String t:tagDTO.getTags()){
                validTags.add(t);
            }
        }
        for (String inputTag:tags){
            if (!validTags.contains(inputTag)){
                return false;
            }
        }
        return true;
    }


}
