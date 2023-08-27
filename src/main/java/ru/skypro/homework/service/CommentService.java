package ru.skypro.homework.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Ad;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.exception.CommentNotFoundException;
import ru.skypro.homework.mapper.CommentMapper;
import ru.skypro.homework.repository.CommentRepository;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Collection;

@Slf4j
@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentMapper commentMapper;

    private final CommentRepository commentRepository;

    private final AdGetter adGetter;

    private final CurrentUserService currentUserService;

    public Comments getAllByAdId(int adId) {
        return commentMapper.commentsToDto(commentRepository.findByAdId(adId));
    }

    /** Throws CommentNotFoundException if the comment entry with the specified adId and commentId
     * is not listed in the db
     */
    private Comment getComment(int adId, int commentId) {
        log.trace("getComment(adId={}, commentId={})", adId, commentId);
        Collection<Comment> commentCollection = commentRepository.findByIdAndAdId(commentId, adId);
        if(commentCollection.isEmpty()) {
            throw new CommentNotFoundException(adId, commentId);
        }
        return commentCollection.stream().iterator().next();
    }

    public CommentDto create(int adId, CreateOrUpdateComment createOrUpdateComment) {
        log.trace("create(adId={}, createOrUpdateComment={})", adId, createOrUpdateComment);

        Ad ad = adGetter.getAd(adId);

        Comment comment = commentMapper.createOrUpdateCommentToComment(createOrUpdateComment);
        comment.setAd(ad);
        comment.setAuthor(currentUserService.getCurrentUser());
        comment.setCreatedAt(LocalDateTime.now().toEpochSecond(ZoneOffset.UTC));
        commentRepository.save(comment);

        return commentMapper.commentToDto(comment);
    }

    public CommentDto edit(int adId, int commentId, CreateOrUpdateComment createOrUpdateComment) {
        log.trace("edit(adId={}, commentId={}, createOrUpdateComment={})", adId, commentId, createOrUpdateComment);

        Comment comment = getComment(adId, commentId);
        comment.setText(createOrUpdateComment.getText());

        commentRepository.save(comment);

        return commentMapper.commentToDto(comment);
    }

    public void delete(int adId, int commentId) {
        log.trace("delete(adId={}, commentId={})", adId, commentId);

        commentRepository.delete(getComment(adId, commentId));
    }
}
