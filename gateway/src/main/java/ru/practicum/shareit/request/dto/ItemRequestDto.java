package ru.practicum.shareit.request.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.practicum.shareit.item.dto.ItemDto;

import java.time.LocalDateTime;
import java.util.List;

@Data
@EqualsAndHashCode(of = {"id"})
@Builder
public class ItemRequestDto {
    private Long id;

    @NotBlank
    @Size(max = 200)
    private String description;

    private Long requesterId;
    private LocalDateTime created;
    private List<AnswerDto> answers;
    private ItemDto itemDto;
}
