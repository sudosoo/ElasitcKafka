package com.sudosoo.takeiteasy.dto.post;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.lang.Nullable;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class CreatePostRequestDto {
    @NotNull(message = "제목을 입력해 주세요.")
    @Size(max = 100,message = "제목은 100자를 넘을 수 없습니다.")
    private String title;
    @NotNull(message = "내용을 입력해 주세요.")
    @Size(max = 500, message = "내용은 500자를 넘을 수 없습니다.")
    private String content;
    @NotNull(message = "멤버아이디를 입력해 주세요.")
    private Long memberId;
    @NotNull(message = "카테고리아이디를 입력해 주세요.")
    private Long categoryId;
}
