package com.jackdyt.blog.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;



@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageDTO<T> {
    private List<T> data;
    private boolean showPrev;
    private boolean showFirstPage;
    private boolean showNext;
    private boolean showFinalPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer pageNeed;

    public void setPageInit(Integer total, Integer page, Integer size) {
        if(total % size == 0){
            pageNeed = total / size;
        }else{
            pageNeed = total/size + 1;
        }
        //should do it on front

        this.page = page;
        pages.add(page);
        for (int i=1; i<4; i++){
            if (page-i > 0){
                pages.add(0,page-i);
            }
            if ((page + i) <= pageNeed){
                pages.add(page+i);
            }
        }

        if (page!=1){
            showPrev=true;
        }
        if (page != pageNeed){
            showNext=true;
        }
        if (!pages.contains(1) ){
            showFirstPage=true;
        }
        if (!pages.contains(pageNeed)){
            showFinalPage=true;
        }

    }
}
