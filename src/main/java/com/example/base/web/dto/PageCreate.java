package com.example.base.web.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;



@Getter
@Setter
@NoArgsConstructor
public class PageCreate {

    @Min(value = 0, message = "페이지는 0보다 커야 합니다.")
    private int page;

    @Min(value = 0, message = "페이지 크기는 0보다 커야 합니다.")
    @Max(value = 1000, message = "페이지 크기는 1000보다 작아야 합니다.")
    private int size;

    private PageCreate(int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("페이지(page)는 0보다 커야합니다.");
        }

        if (size < 1) {
            throw new IllegalArgumentException("페이지크기(size)는 1보다 커야 합니다.");
        }

        this.page = page;
        this.size = size;
    }

    public static PageCreate of(int page, int size) {
        return new PageCreate(page, size);
    }

    @JsonIgnore
    public long getOffset() {
        return (long) page * (long) size;
    }
}