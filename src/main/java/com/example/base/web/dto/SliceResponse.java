package com.example.base.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class SliceResponse<T> {
    private List<T> content = new ArrayList<>();
    private PageCreate pageCreate;
    private boolean hasNext;
    private SliceResponse(List<T> content, PageCreate pageCreate, boolean hasNext) {
        this.content.addAll(content);
        this.pageCreate = pageCreate;
        this.hasNext = hasNext;
    }

    public static <T> SliceResponse<T> of(List<T> content, PageCreate pageCreate, boolean hasNext) {
        return new SliceResponse<>(content, pageCreate, hasNext);
    }

    @JsonProperty("hasPrevious")
    public boolean hasPrevious(){
        return this.pageCreate.getPage() > 0;
    }

    @JsonProperty("hasContent")
    public boolean hasContent() {
        return !getContent().isEmpty();
    }
}
