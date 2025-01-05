package com.blinker.atom.config.common;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
public class PageResponse<T> {
    private List<T> content;
    private int numberOfElements;
    private int pageNumber;
    private long offset;
    private int pageSize;
    private long totalElements;
    private int totalPage;

    @Builder
    public PageResponse(Page<T> page) {
        this.content = page.getContent();
        this.numberOfElements = page.getNumberOfElements();
        this.pageNumber = page.getPageable().getPageNumber();
        this.offset = page.getPageable().getOffset();
        this.pageSize = page.getPageable().getPageSize();
        this.totalElements = page.getTotalElements();
        this.totalPage = page.getTotalPages();
    }

    public PageResponse(List<T> content) {
        this.content = content;
        this.numberOfElements = content.size();
        this.pageNumber = 0;
        this.offset = content.size();
        this.pageSize = 0;
        this.totalElements = content.size();
        this.totalPage = 0;
    }

}
