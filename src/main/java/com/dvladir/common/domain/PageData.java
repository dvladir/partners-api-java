package com.dvladir.common.domain;


import java.util.List;
import java.util.Objects;

public class PageData<T> {
    private List<T> data;
    private int total;
    private int pageSize;
    private int pageNum;
    private int pagesCount;

    public PageData() {
    }

    public PageData(List<T> data, int total, int pageSize, int pageNum, int pagesCount) {
        this.data = data;
        this.total = total;
        this.pageSize = pageSize;
        this.pageNum = pageNum;
        this.pagesCount = pagesCount;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
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

    public int getPagesCount() {
        return pagesCount;
    }

    public void setPagesCount(int pagesCount) {
        this.pagesCount = pagesCount;
    }

    @Override
    public String toString() {
        return "PageData{" +
                "data=" + data +
                ", total=" + total +
                ", pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", pageCount=" + pagesCount +
                '}';
    }
}
