package com.example.base.comment.controller.request;

import com.example.base.comment.domain.ReferenceType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
public record CommentCreateRequest(
        @Schema(description = "댓글 작성자 이름", example = "홍길동")
        @Length(min = 1, max = 50, message = "1자리 이상 50자리 이하로 입력해주세요.")
        String name,

        @Schema(description = "댓글 작성자 이메일", example = "1234")
        @Length(min = 4, max = 4, message = "4자리의 숫자만 입력 가능합니다.")
        @Pattern(regexp = "^\\d{4}$", message = "4자리의 숫자만 입력 가능합니다.")
        String password,

        @Schema(description = "댓글 내용", example = "댓글 내용")
        @Length(min = 1, max = 250, message = "1자리 이상 250자리 이하로 입력해주세요.")
        String content,

        @Schema(description = "댓글 참조 타입", allowableValues = {"FOOD, REVIEW"})
        @NotNull(message = "댓글 참조 타입을 입력해주세요.")
        ReferenceType referenceType,

        @Schema(description = "댓글 참조 ID", example = "1")
        @NotNull(message = "댓글 참조 ID를 입력해주세요.")
        Long referenceId
) {
}
