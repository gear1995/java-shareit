package ru.practicum.shareit.request.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.request.dto.AnswerDto;
import ru.practicum.shareit.request.model.Answer;

@UtilityClass
public class AnswerMapper {
    public Answer toAnswer(final AnswerDto answerDto) {
        if (answerDto == null) return null;

        return Answer.builder()
                .id(answerDto.getId())
                .build();
    }

    public AnswerDto toAnswerDto(final Answer answer) {
        if (answer == null) return null;

        return AnswerDto.builder()
                .id(answer.getId())
                .itemId(answer.getItem().getId())
                .ownerId(answer.getOwner().getId())
                .build();
    }
}
