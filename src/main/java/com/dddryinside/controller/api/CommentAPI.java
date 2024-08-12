package com.dddryinside.controller.api;

import com.dddryinside.DTO.PostCommentRequest;
import com.dddryinside.service.CommentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
public class CommentAPI {
    private final CommentService commentService;

    public CommentAPI(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/post-comment")
    public void postComment(@RequestBody PostCommentRequest postCommentRequest) {
        commentService.save(postCommentRequest);
    }
}
