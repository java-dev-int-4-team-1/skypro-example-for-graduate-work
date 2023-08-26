package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Comment;

import java.util.Collection;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface CommentMapper {
    Comment createOrUpdateCommentToComment(CreateOrUpdateComment createOrUpdateComment);

    CommentDto commentToDto(Comment comment);

    @Mapping(source="comments.size()", target="count")
    default Comments commentsToDto(Collection<Comment> comments) {
        Comments result = new Comments();
        result.setResults(
                comments.stream()
                        .map(this::commentToDto)
                        .collect(Collectors.toList())
        );
        return result;
    }

}
