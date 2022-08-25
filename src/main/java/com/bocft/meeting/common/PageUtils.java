package com.bocft.meeting.common;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class PageUtils<T>  {
    private Integer page;
    private Integer limit;

    public PageUtils(Integer page,Integer limit){
        if (page == null) {
            this.page=(int)Constant.PAGE;
        }else {
            this.page=page;
        }

        if (limit==null){
            this.limit=Constant.SIZE;
        }else {
            this.limit=limit;
        }

    }
    public static Page CurrentPage(Long current, Long size, List<?> records) {
        if (current==null) {
            current=Constant.PAGE;
        }
        if (size==null) {
            size=Constant.LIMIT;
        }
        Page page=new Page();
        int len = records.size();
        page.setTotal(len);
        page.setCurrent(current);
        page.setSize(size);
        int value = size.intValue();
        List list=new ArrayList();
        int right = (current.intValue() ) * value ;
        int left=right-value;
        if (right<=len) {
            for (int i = left; i <right ; i++) {
                list.add(records.get(i));
            }
        }else {
            for (int i = left; i < len; i++) {
                list.add(records.get(i));
            }
        }
        page.setRecords(list);
        return page;
    }
    public static Page CurrentPage(Long current, Long size) {
        if (current == null) {
            current = Constant.PAGE;
        }
        if (size == null) {
            size = Constant.LIMIT;
        }
        return new Page(current,size);
    }
}
