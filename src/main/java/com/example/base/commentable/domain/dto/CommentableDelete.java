package com.example.base.commentable.domain.dto;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.hibernate.validator.constraints.Length;

@Builder
@Schema(name = "Delete", description = "delete request")
public record CommentableDelete(

    @Schema(description = "Password", example = "1234")
    @Length(min = 4, max = 4, message = "Password must be 4 digits")
    String password
) {
}
