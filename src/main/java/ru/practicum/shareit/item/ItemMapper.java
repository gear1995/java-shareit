package ru.practicum.shareit.item;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@UtilityClass
public class ItemMapper {

    public Item toItemWithOwner(final ItemDto itemDto, final int ownerId) {
        log.info("Начало преобразования itemDto в item c полем ownerId: {}", ownerId);

        if (itemDto == null) {
            return null;
        }

        Item item = Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .owner(ownerId)
                .build();
        log.info("Преобразование ItemDto в Item успешно завершено с ownerId: {}", ownerId);

        return item;
    }

    public Item toItem(final ItemDto itemDto) {
        log.info("Начало преобразования itemDto в item");

        if (itemDto == null) {
            return null;
        }

        Item item = Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
        log.info("Преобразование ItemDto в Item успешно завершено");

        return item;
    }

    public ItemDto toItemDto(final Item item) {
        log.info("Начало преобразования item в itemDto");

        if (item == null) {
            return null;
        }

        ItemDto itemDto = ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();

        log.info("Преобразование Item в ItemDto успешно завершено");

        return itemDto;
    }

    public static List<ItemDto> toItemDtoList(Collection<Item> itemCollection) {
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (Item item : itemCollection) {
            itemDtoList.add(ItemMapper.toItemDto(item));
        }

        return itemDtoList;
    }

    public static List<Item> toItemList(Collection<ItemDto> itemDtoCollection) {
        List<Item> itemDtoList = new ArrayList<>();
        for (ItemDto item : itemDtoCollection) {
            itemDtoList.add(ItemMapper.toItem(item));
        }

        return itemDtoList;
    }
}
