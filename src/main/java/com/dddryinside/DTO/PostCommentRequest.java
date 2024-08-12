package com.dddryinside.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@Getter
@Setter
@NoArgsConstructor
public class PostCommentRequest {
    private String text;
    private Long imageId;
}
