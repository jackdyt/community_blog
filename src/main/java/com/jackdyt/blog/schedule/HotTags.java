package com.jackdyt.blog.schedule;

import com.jackdyt.blog.cache.HotTagCache;
import com.jackdyt.blog.dto.HotTagDTO;
import com.jackdyt.blog.mapper.EssayMapper;
import com.jackdyt.blog.model.Essay;
import com.jackdyt.blog.model.EssayExample;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
public class HotTags {
    @Autowired
    private EssayMapper essayMapper;


    //@Scheduled(cron = "0 0 3 * * *")
    @Scheduled(fixedRate = 10000)
    public void hotTagSchedule(){


        int priority = 0, score = 0;
        Map<String, Integer> tagPriority = HotTagCache.getHotTags();

        List<Essay> essays = essayMapper.selectByExample(new EssayExample());
        for (Essay essay:essays){
            String[] tags = essay.getTag().split(",");
            score = 3 + 2*essay.getCommentCount() + essay.getViewCount();
            for (String tag : tags) {
                priority = score + tagPriority.getOrDefault(tag, 0);
                tagPriority.put(tag, priority);
            }

        }

//        HotTagCache.getHotTags().forEach(
//                (k,v)->{
//                    System.out.println(k+":" + v + "");
//                }
//        );
        HotTagCache.update();

    }

}
