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
    private int size;
    private boolean hasNext;
    private SliceResponse(List<T> content, int size, boolean hasNext) {
        this.content.addAll(content);
        this.size = size;
        this.hasNext = hasNext;
    }

    public static <T> SliceResponse<T> of(List<T> content, int size, boolean hasNext) {
        return new SliceResponse<>(content, size, hasNext);
    }

    @JsonProperty("hasContent")
    public boolean hasContent() {
        return !getContent().isEmpty();
    }
}
