package ru.practicum.shareit.item.repository;

import ru.practicum.shareit.item.model.Item;

import java.util.List;
import java.util.Set;

public interface ItemRepository {
    Item create(Item item);

    Item getItemById(int itemId);

    Item update(Item item, int itemId);

    List<Item> getUserItems(int userId);

    Set<Item> getItemsBySearchParam(String query);
}
