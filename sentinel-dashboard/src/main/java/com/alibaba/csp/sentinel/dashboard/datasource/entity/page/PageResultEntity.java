package com.alibaba.csp.sentinel.dashboard.datasource.entity.page;

import com.alibaba.csp.sentinel.dashboard.datasource.entity.rule.RuleEntity;
import com.alibaba.csp.sentinel.dashboard.domain.Result;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zeryts
 * @description: 分页返回的数据
 * -----------------------------------
 * @title: PageResultEntity
 * @projectName amap
 * @date 2019/9/24 11:31
 */
public class PageResultEntity<T> extends Result<T> {
    /**
     * 总条数
     */
    private Integer totalCount;
    /**
     * 总页数
     */
    private Integer totalPage;
    /**
     * 当前页
     */
    private Integer page;
    /**
     * 页大小
     */
    private Integer limit;


    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }
    /** description: Rule操作的分页方法
     * @param data Rule分页的所有数据
     * @param page 前端传递的数据
     * @return: com.alibaba.csp.sentinel.dashboard.datasource.entity.page.PageResultEntity<java.util.List<T>>
     * @Author: zeryts
     * @Date: 2019/9/24 14:11
     */
    public static <T extends RuleEntity> PageResultEntity<List<T>> ofSuccess(List<T> data, PageEntity<T> page) {
        PageResultEntity<List<T>> res = new PageResultEntity<List<T>>() ;
        res.setSuccess(true);
        res.setMsg("success");
        int totalPage = (data.size() + page.getLimit() - 1) / page.getLimit();
        List<T> topResource = new ArrayList<>();
        Integer pageIndex = page.getPage();
        Integer pageSize = page.getLimit();
        if (page.getPage() <= totalPage) {
            topResource = data.subList((page.getPage() - 1) * pageSize,
                    Math.min(pageIndex * pageSize, data.size()));
        }
        res.setData(topResource);
        res.setPage(pageIndex);
        res.setLimit(pageSize);
        res.setTotalCount(data.size());
        res.setTotalPage(totalPage);
        return res;


    }
}
