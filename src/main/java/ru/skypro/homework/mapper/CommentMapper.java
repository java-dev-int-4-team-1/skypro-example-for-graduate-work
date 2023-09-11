package ru.skypro.homework.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.CommentDto;
import ru.skypro.homework.dto.Comments;
import ru.skypro.homework.dto.CreateOrUpdateComment;
import ru.skypro.homework.entity.Comment;
import ru.skypro.homework.entity.User;

import java.util.Collection;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public abstract class CommentMapper extends ImageMapper {

    public abstract Comment map(CreateOrUpdateComment createOrUpdateComment);

    @Mapping(source="id", target="pk")
    @Mapping(source="author.id", target="author")
    @Mapping(source="author.firstName", target="authorFirstName")
    @Mapping(expression="java(mapImage(comment.getAuthor()))", target="authorImage")
    public abstract CommentDto map(Comment comment);

    public String mapImage(User author) {
        return buildImageMapping(realmUsers, author);
    }

    public Comments map(Collection<Comment> comments) {
        Comments result = new Comments();
        result.setResults(
                comments.stream()
                        .map(this::map)
                        .collect(Collectors.toList())
        );
        result.setCount(comments.size());
        return result;
    }

}
