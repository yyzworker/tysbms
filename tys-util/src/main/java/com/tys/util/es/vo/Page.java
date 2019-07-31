package com.tys.util.es.vo;

import java.io.Serializable;
import java.util.List;

/**
 * @Author haoxu
 * @Date 2019/5/23 9:35
 **/
public class Page<E> implements Serializable {
    private int total;
    private List<E> content;
    private int pageSize = 10;
    private int pageNum = 1;
    private int totalSize;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
        this.totalSize = (int)Math.ceil(total/pageSize);
    }

    public List<E> getContent() {
        return content;
    }

    public void setContent(List<E> content) {
        this.content = content;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(int totalSize) {
        this.totalSize = totalSize;
    }
}
