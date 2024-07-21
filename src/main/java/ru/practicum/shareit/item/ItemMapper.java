package ru.practicum.shareit.item;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

@Slf4j
@UtilityClass
public class ItemMapper {

    public Item toItem(final ItemDto itemDto) {
        log.info("Начало преобразования itemDto в item");

        if (itemDto == null) {
            return null;
        }

        Item item = Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .status(itemDto.getStatus())
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
                .status(item.getStatus())
                .build();

        log.info("Преобразование Item в ItemDto успешно завершено");

        return itemDto;
    }
}
