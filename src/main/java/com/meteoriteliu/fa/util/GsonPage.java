package com.meteoriteliu.fa.util;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Created by meteo on 2017/3/22.
 */
public class GsonPage<T> {
    private List<T> content;
    private int totalPage;
    private int currentPage;

    public GsonPage(Page<T> page) {
        content = page.getContent();
        totalPage = page.getTotalPages();
        currentPage = page.getNumber();
    }

    public List<T> getContent() {
        return content;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public int getCurrentPage() {
        return currentPage;
    }
}
