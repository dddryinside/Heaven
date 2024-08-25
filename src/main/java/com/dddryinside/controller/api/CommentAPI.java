package com.dddryinside.controller.api;

import com.dddryinside.requests.EditCommentRequest;
import com.dddryinside.requests.PostCommentRequest;
import com.dddryinside.exeptions.APIException;
import com.dddryinside.model.Comment;
import com.dddryinside.service.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Void> postComment(@RequestBody PostCommentRequest postCommentRequest) {
        try {
            commentService.save(postCommentRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getHttpStatus());
        }
    }

    @PostMapping("/delete-comment")
    public ResponseEntity<Void> deleteComment(@RequestParam Long commentId) {
        try {
            commentService.delete(commentId);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getHttpStatus());
        }
    }

    @PostMapping("/edit-comment")
    public ResponseEntity<Void> editComment(@RequestBody EditCommentRequest editCommentRequest) {
        try {
            commentService.edit(editCommentRequest);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getHttpStatus());
        }
    }

    @GetMapping("/get-comments")
    public ResponseEntity<List<Comment>> getComments(@RequestParam Long imageId, @RequestParam String page) {
        try {
            return new ResponseEntity<>(commentService.getComments(imageId, page), HttpStatus.OK);
        } catch (APIException e) {
            return new ResponseEntity<>(e.getHttpStatus());
        }
    }
}
