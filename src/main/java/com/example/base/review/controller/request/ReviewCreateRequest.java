package com.example.base.review.controller.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
@Schema(description = "리뷰 생성 요청")
public record ReviewCreateRequest(
    @Schema(description = "리뷰 이름", example = "50대의 수면 내시경 후기")
    @Length(min = 1, max = 50, message = "후기 이름은 1자 이상 50자 이하로 입력해주세요.")
    String name,

    @Schema(description = "비밀번호", example = "1234")
    @Length(min = 4, max = 4, message = "비밀번호는 4자로 입력해주세요.")
    @Pattern(regexp = "^\\d{4}$", message = "4자리의 숫자만 입력 가능합니다.")
    String password,

    @Schema(description = "후기", example = "후기입니다")
    @Length(min = 1, max = 255, message = "후기는 1자 이상 255자 이하로 입력해주세요.")
    String content,
    @Schema(description = "나이", allowableValues = {"20", "30", "40", "50", "60", "70", "80", "90"})
    int age,

    @Schema(description = "성별", example = "M", allowableValues = {"M", "F"})
    char gender,

    @Schema(description = "수면 여부", allowableValues = {"true", "false"})
    boolean sleepFlag,

    @Schema(description = "결과", example = "[\"정상\", \"비정상\"]")
    String[] result
) {
}
