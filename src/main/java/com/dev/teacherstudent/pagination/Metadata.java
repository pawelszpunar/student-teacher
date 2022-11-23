package com.dev.teacherstudent.pagination;

public class Metadata {

    private final int totalPages;
    private final long totalElements;
    private final int size;
    private final int page;

    public Metadata(int totalPages, long totalElements, int size, int page) {
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.size = size;
        this.page = page;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public long getTotalElements() {
        return totalElements;
    }

    public int getSize() {
        return size;
    }

    public int getPage() {
        return page;
    }

}
