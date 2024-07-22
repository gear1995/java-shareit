package ru.practicum.shareit.item;

import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Set;

public interface ItemRepository {
    Item create(ItemDto itemDto, int userId);

    Item getItemById(int itemId);

    Item update(ItemDto itemDto, int itemId);

    List<Item> getUserItems(int userId);

    Set<Item> getItemsBySearchParam(String query);
}
