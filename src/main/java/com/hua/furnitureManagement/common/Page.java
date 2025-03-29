package com.hua.furnitureManagement.common;

import lombok.Data;

import java.util.List;

@Data
public class Page<T> {
    //当前页码
    private int pageNum;
    //每页显示数量
    private int pageSize;
    //总记录数
    private int totalCount;
    //总页数
    private int totalPage;

    private List<T> data;

}
