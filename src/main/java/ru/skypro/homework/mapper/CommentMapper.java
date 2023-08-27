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

    @Mapping(source="id", target="pk")
    @Mapping(source="ad.author.id", target="author")
    @Mapping(source="ad.author.firstName", target="authorFirstName")
    @Mapping(source="ad.author.image", target="authorImage")
    CommentDto commentToDto(Comment comment);

    default Comments commentsToDto(Collection<Comment> comments) {
        Comments result = new Comments();
        result.setResults(
                comments.stream()
                        .map(this::commentToDto)
                        .collect(Collectors.toList())
        );
        result.setCount(comments.size());
        return result;
    }

}
