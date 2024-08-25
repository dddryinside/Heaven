package com.dddryinside.requests;

import lombok.Data;

@Data
public class EditCommentRequest {
    private Long commentId;
    private String newCommentText;
}
