package ru.practicum.shareit.request.mapper;

import lombok.experimental.UtilityClass;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.model.ItemRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@UtilityClass
public class ItemRequestMapper {
    public ItemRequest toItemRequest(final ItemRequestDto itemRequestDto) {
        if (itemRequestDto == null) return null;

        return ItemRequest.builder()
                .id(itemRequestDto.getId())
                .description(itemRequestDto.getDescription())
                .created(itemRequestDto.getCreated())
                .requesterId(itemRequestDto.getRequesterId())
                .answers(itemRequestDto.getAnswers())
                .build();
    }

    public ItemRequestDto toItemRequestDto(final ItemRequest itemRequest) {
        if (itemRequest == null) return null;

        return ItemRequestDto.builder()
                .id(itemRequest.getId())
                .description(itemRequest.getDescription())
                .created(itemRequest.getCreated())
                .requesterId(itemRequest.getRequesterId())
                .answers(itemRequest.getAnswers())
                .build();
    }

    public static List<ItemRequestDto> toItemRequestDtoList(Collection<ItemRequest> itemRequests) {
        List<ItemRequestDto> itemRequestDtos = new ArrayList<>();

        for (ItemRequest itemRequest : itemRequests) {
            itemRequestDtos.add(ItemRequestMapper.toItemRequestDto(itemRequest));
        }

        return itemRequestDtos;
    }
}
