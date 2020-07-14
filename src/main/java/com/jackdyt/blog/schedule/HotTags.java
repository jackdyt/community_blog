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

        int offset = 0;
        int limit = 5;
        int priority = 0;
        Map<String, Integer> tagPriority = HotTagCache.getHotTags();
        List<Essay> essays = new ArrayList<>();
        while (offset ==0 || essays.size() == limit){
            essays = essayMapper.selectByExampleWithRowbounds(new EssayExample(), new RowBounds(offset, limit));
            for (Essay essay:essays){

                String[] tags = essay.getTag().split(",");
                for (String tag : tags) {
                    priority = 3 + 2*essay.getCommentCount() + essay.getViewCount();
                    tagPriority.put(tag, priority);
                }

            }
            offset+=limit;
        }
//        HotTagCache.getHotTags().forEach(
//                (k,v)->{
//                    System.out.println(k+":" + v + "");
//                }
//        );
        HotTagCache.update();

    }

}
