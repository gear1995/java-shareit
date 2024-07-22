package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

public interface ItemService {
    ItemDto create(ItemDto itemDto, int userId);

    ItemDto update(int itemId, ItemDto itemDto, int userId);

    ItemDto getItemById(int itemId);

    List<ItemDto> getUserItems(int userId);

    List<ItemDto> getItemsBySearchParam(String query);
}
