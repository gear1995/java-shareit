package ru.practicum.shareit.item.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(of = {"id"})
public class Item {
    private int id;
    @NotBlank
    private String owner;
    @NotBlank
    private String name;
    @Size(max = 200)
    private String description;
    private ItemStatus status;
}
