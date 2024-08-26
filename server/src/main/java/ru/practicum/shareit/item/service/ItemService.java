package ru.practicum.shareit.item.service;

import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ExtendedItemDto;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto create(ItemDto itemDto, Long userId);

    ItemDto update(Long itemId, ItemDto itemDto, Long userId);

    ExtendedItemDto getItemById(Long itemId);

    List<ExtendedItemDto> getUserItems(Long userId);

    List<ItemDto> getItemsBySearchParam(String query);

    CommentDto addComment(Long itemId, CommentDto commentDto, Long userId);
}
