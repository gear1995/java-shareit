package ru.practicum.shareit.item.mapper;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.model.Comment;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@UtilityClass
public class CommentMapper {
    public CommentDto toCommentDto(Comment comment) {
        if (comment == null) {
            return null;
        }

        CommentDto commentDto = CommentDto.builder()
                .id(comment.getId())
                .text(comment.getText())
                .authorName(comment.getAuthor().getName())
                .created(comment.getCreated())
                .build();
        log.info("Преобразование Comment в CommentDto успешно завершено");

        return commentDto;
    }

    public List<CommentDto> toCommentDtoList(List<Comment> comments) {
        if (comments == null) {
            return null;
        }

        List<CommentDto> commentDtoList = new ArrayList<>();

        for (Comment comment : comments) {
            commentDtoList.add(toCommentDto(comment));
        }

        return commentDtoList;
    }
}
