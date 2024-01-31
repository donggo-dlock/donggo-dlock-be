package com.example.base.comment.domain.dto;

import com.example.base.comment.domain.ReferenceType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(description = "댓글 검색")
public record CommentSearch(
        @Schema(description = "마지막 ID", defaultValue = "0")
        @PositiveOrZero(message = "0 이상의 숫자를 입력해주세요.")
        Long lastId,

        @Schema(description = "댓글 참조 타입", allowableValues = {"FOOD", "REVIEW"})
        @NotNull(message = "댓글 참조 타입을 입력해주세요.")
        ReferenceType referenceType,

        @Schema(description = "댓글 참조 ID, 게시글의 ID를 입력해주세요", example = "1")
        @Positive(message = "1 이상의 숫자를 입력해주세요.")
        Long referenceId,

        @Schema(description = "댓글 크기, 한 번에 조회할 댓글 개수를 입력해주세요", example = "10")
        @Positive(message = "1 이상의 숫자를 입력해주세요.")
        Integer size
) {
}
