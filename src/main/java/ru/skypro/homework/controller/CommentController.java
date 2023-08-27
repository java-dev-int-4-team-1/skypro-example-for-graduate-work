package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.service.CommentService;

@Slf4j
@CrossOrigin(value = "${cross-origin.value}")
@RestController
@RequiredArgsConstructor
@RequestMapping("/ads/{adId}/comments")
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public Comments getComments(    @PathVariable("adId") Integer adId) {

        return commentService.getAllByAdId(adId);
    }

    @PostMapping
    public CommentDto postComment(  @PathVariable("adId") Integer adId,
                                    @RequestBody CreateOrUpdateComment createOrUpdateComment) {
        return commentService.create(adId, createOrUpdateComment);
    }

    @PatchMapping("/{commentId}")
    public CommentDto patchComment(@PathVariable("adId") Integer adId,
                                   @PathVariable("commentId") Integer commentId,
                                   @RequestBody CreateOrUpdateComment createOrUpdateComment) {
        return commentService.edit(adId, commentId, createOrUpdateComment);
    }

    @DeleteMapping("/{commentId}")
    public void deleteComment(@PathVariable("adId") Integer adId,
                                              @PathVariable("commentId") Integer commentId) {
        commentService.delete(adId, commentId);
    }

}
