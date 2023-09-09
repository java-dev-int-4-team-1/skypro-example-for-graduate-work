package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;

@Slf4j
@CrossOrigin(value = "${crossorigin.url}")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads/{adId}/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public Comments getComments(
            @PathVariable("adId") Integer adId
    ) {
        log.trace("getComments(adId={})", adId);
        return commentService.getAllByAdId(adId);
    }

    @PostMapping
    public CommentDto postComment(
            @PathVariable("adId") Integer adId,
            @RequestBody CreateOrUpdateComment createdComment) {
        log.trace("postComment(adId={}, createdComment={})", adId, createdComment);
        return commentService.create(adId, createdComment);
    }

    @PatchMapping("/{commentId}")
    public CommentDto patchComment(
            @PathVariable("adId") Integer adId,
            @PathVariable("commentId") Integer commentId,
            @RequestBody CreateOrUpdateComment updatedComment) {
        log.trace("patchComment(adId={}, commentId={}, updatedComment={})",
                adId, commentId, updatedComment);
        return commentService.edit(adId, commentId, updatedComment);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable("adId") Integer adId,
                                              @PathVariable("commentId") Integer commentId) {
        log.trace("deleteComment(adId={}, commentId={})", adId, commentId);
        commentService.delete(adId, commentId);
    }

}
