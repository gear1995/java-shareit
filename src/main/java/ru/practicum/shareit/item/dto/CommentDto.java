package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(of = {"id"})
@Builder
public class CommentDto {
    private Long id;
    @NotBlank
    private String text;
    @NotBlank
    private String authorName;
    @NotBlank
    private LocalDateTime created;
}
