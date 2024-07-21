package ru.practicum.shareit.item.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import ru.practicum.shareit.item.model.ItemStatus;

@Data
@Builder
public class ItemDto {
    private int id;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    private ItemStatus status;
    private int rentCount;
}
