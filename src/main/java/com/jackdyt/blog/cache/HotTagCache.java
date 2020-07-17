package com.jackdyt.blog.cache;

import com.jackdyt.blog.dto.HotTagDTO;

import java.util.*;

public class HotTagCache {
    private static final Map<String, Integer> hotTags = new HashMap<>();
    private static List<HotTagDTO> hotTagDTOS= new ArrayList<>();
    public static synchronized Map<String, Integer> getHotTags(){
        hotTags.clear();
        return hotTags;
    }

    public static synchronized List<HotTagDTO> getHotTagDTOS(){
        return hotTagDTOS;
    }

    public static void update(){
        hotTagDTOS.clear();
        PriorityQueue<HotTagDTO> priorityQueue = new PriorityQueue<>((a,b) -> a.getCount()-b.getCount());
        int max = 5;
        hotTags.forEach((name, count)->{
            HotTagDTO hotTagDTO = new HotTagDTO();
            hotTagDTO.setCount(count);
            hotTagDTO.setName(name);
            priorityQueue.add(hotTagDTO);
            if (priorityQueue.size() > max){
                priorityQueue.poll();
            }
                });

        while (!priorityQueue.isEmpty()){
            hotTagDTOS.add(0,priorityQueue.poll());
        }
//        System.out.println(hotTagDTOS);
        return;
    }
}
