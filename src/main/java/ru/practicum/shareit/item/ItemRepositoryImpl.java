package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Repository
@RequiredArgsConstructor
@Slf4j
public class ItemRepositoryImpl implements ItemRepository {
    List<Item> items = new ArrayList<>();

    @Override
    public Item create(ItemDto newItemDto, int userId) {
        int itemId = ItemId.getItemId();
        newItemDto.setId(itemId);
        itemId++;
        ItemId.setItemId(itemId);

        Item item = ItemMapper.toItem(newItemDto, userId);
        items.add(item);
        return item;
    }

    @Override
    public Item getItemById(int itemId) {
        for (Item item : items) {
            if (item.getId() == itemId) {
                return item;
            }
        }
        throw new NotFoundException("Item with id " + itemId + " not found");
    }

    @Override
    public Item update(ItemDto updatedItemDto, int itemId) {
        Item item = getItemById(itemId);
        if (updatedItemDto.getName() != null) {
            item.setName(updatedItemDto.getName());
        }

        if (updatedItemDto.getDescription() != null) {
            item.setDescription(updatedItemDto.getDescription());
        }
        if (updatedItemDto.getAvailable() != null) {
            item.setAvailable(updatedItemDto.getAvailable());
        }

        return item;
    }

    @Override
    public List<Item> getUserItems(int userId) {
        List<Item> userItems = new ArrayList<>();
        for (Item item : items) {
            if (item.getOwner() == userId) {
                userItems.add(item);
            }
        }

        return userItems;
    }

    @Override
    public Set<Item> getItemsBySearchParam(String query) {
        if (query == null || query.isBlank()) {
            return new HashSet<>();
        }
        Set<Item> items = new HashSet<>();
        for (Item item : this.items) {
            if (containsIgnoreCase(item.getName(), query) && item.getAvailable()) {
                items.add(item);
            }
            if (containsIgnoreCase(item.getDescription(), query) && item.getAvailable()) {
                items.add(item);
            }
        }

        return items;
    }

    public boolean containsIgnoreCase(String mainString, String subString) {
        return mainString.toLowerCase().contains(subString.toLowerCase());
    }

}
