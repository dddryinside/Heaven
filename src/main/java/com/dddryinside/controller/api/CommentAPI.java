package com.dddryinside.controller.api;

import com.dddryinside.DTO.CommentView;
import com.dddryinside.DTO.PostCommentRequest;
import com.dddryinside.model.Comment;
import com.dddryinside.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @GetMapping("/get-comments")
    public List<CommentView> getComments(@RequestParam("imageId") Long imageId,
                                         @RequestParam("offset") int offset,
                                         @RequestParam("limit") int limit) {
        return commentService.getComments(imageId, offset, limit);
    }
}
