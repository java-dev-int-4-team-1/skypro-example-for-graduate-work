package ru.skypro.homework.mapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.testutil.AdTestUtil;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class CommentMapperTest extends AdTestUtil {

    @Autowired
    private CommentMapper commentMapper;

    private Comment generateComment() {
        Comment comment = new Comment();
        comment.setAd(generateAd());
        comment.setText(SOME_TEXT);
        return comment;
    }

    @Test
    void commentToCommentDto() {
        //given
        Comment comment = generateComment();

        //when
        CommentDto commentDto = commentMapper.commentToDto(comment);

        //then
        assertEqual(comment, commentDto);
    }

    private static void assertEqual(Comment comment, CommentDto commentDto) {
        assertThat(commentDto).isNotNull();
        assertThat(commentDto.getPk()).isEqualTo(comment.getId());
        assertThat(commentDto.getText()).isEqualTo(comment.getText());
        assertThat(commentDto.getAuthor()).isEqualTo(comment.getAd().getAuthor().getId());
        assertThat(commentDto.getAuthorFirstName()).isEqualTo(comment.getAd().getAuthor().getFirstName());
        assertThat(commentDto.getAuthorImage()).isEqualTo(comment.getAd().getAuthor().getImage());
        assertThat(commentDto.getCreatedAt()).isEqualTo(comment.getCreatedAt());
    }

    @Test
    void commentsToDtos() {
        //given
        Collection<Comment> commentsCollection = List.of(generateComment());

        //when
        Comments comments = commentMapper.commentsToDto(commentsCollection);

        //then
        assertThat(comments).isNotNull();
        assertThat(comments.getCount()).isEqualTo(commentsCollection.size());
        Collection<CommentDto> dtos = comments.getResults();
        assertThat(dtos).isNotNull();
        assertThat(dtos.size()).isEqualTo(commentsCollection.size());

        Iterator<Comment> iterator = commentsCollection.iterator();
        dtos.forEach(dto -> assertEqual(iterator.next(), dto));
    }

    @Test
    void createOrUpdateCommentToComment() {
        //given
        CreateOrUpdateComment createOrUpdateComment = new CreateOrUpdateComment();
        createOrUpdateComment.setText(SOME_TEXT);

        //when
        Comment comment =  commentMapper.createOrUpdateCommentToComment(
                createOrUpdateComment);

        //then
        assertThat(comment).isNotNull();
        assertThat(comment.getText()).isEqualTo(createOrUpdateComment.getText());
    }


}

