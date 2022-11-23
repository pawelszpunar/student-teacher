package com.dev.teacherstudent.pagination;

import java.util.List;

public class ListResponse<T> {

    private final Metadata metadata;
    private final List<T> content;

    public ListResponse(List<T> content,
                        int totalPages,
                        long totalElements,
                        int size,
                        int page) {

        this.metadata = new Metadata(totalPages, totalElements, size, page);
        this.content = content;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public List<T> getContent() {
        return content;
    }

}
