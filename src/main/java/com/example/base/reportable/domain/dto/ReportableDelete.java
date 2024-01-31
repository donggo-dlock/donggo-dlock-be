package com.example.base.reportable.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
@Schema(name = "Delete", description = "delete request")
public record ReportableDelete(

        @Schema(description = "Password", example = "1234")
        @Length(min = 4, max = 4, message = "비밀번호는 4자리를 입력해주세요")
        @Pattern(regexp = "^\\d{4}$", message = "비밀번호는 숫자만 입력해주세요")
        String password

) {
}
