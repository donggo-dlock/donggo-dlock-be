package com.example.base.web.dto;

import com.example.base.post.controller.response.PostResponse;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Getter
@NoArgsConstructor
public class PageResponse<T> {
    private List<T> content = new ArrayList<>();
    private PageCreate pageCreate;
    private long total;

    private PageResponse(List<T> content, PageCreate pageCreate, long total) {
        this.content.addAll(content);
        this.pageCreate = pageCreate;
        this.total = Optional.of(pageCreate)
                .filter(it -> !content.isEmpty())
                .filter(it -> pageCreate.getOffset() + it.getSize() > total)
                .map(it -> pageCreate.getOffset() + content.size())
                .orElse(total);
    }

    public static <T> PageResponse<T> of(List<T> content, PageCreate pageCreate, long total) {
        return new PageResponse<>(content, pageCreate, total);
    }

    public PageResponse<PostResponse> updateType(List<T> content) {
        this.content.clear();
        this.content.addAll(content);
        return (PageResponse<PostResponse>) this;
    }

    public int getTotalPages() {
        return this.pageCreate.getSize() == 0 ? 1 : (int) Math.ceil(getTotal() / (double) this.pageCreate.getSize());
    }

    @JsonProperty("hasPrevious")
    public boolean hasPrevious(){
        return this.pageCreate.getPage() > 0;
    }

    @JsonProperty("isFirst")
    public boolean isFirst() {
        return !hasPrevious();
    }

    @JsonProperty("hasNext")
    public boolean hasNext() {
        return this.pageCreate.getPage() + 1 < getTotalPages();
    }

    @JsonProperty("isLast")
    public boolean isLast() {
        return !hasNext();
    }

    @JsonProperty("hasContent")
    public boolean hasContent() {
        return !getContent().isEmpty();
    }
}
