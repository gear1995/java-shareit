package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"id"})
@Builder
public class AnswerDto {
    private Long id;

    @NotNull
    private Long itemId;

    @NotNull
    private Long ownerId;
}
