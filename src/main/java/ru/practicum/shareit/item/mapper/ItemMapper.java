package ru.practicum.shareit.item.mapper;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.item.dto.ExtendedItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Slf4j
@UtilityClass
public class ItemMapper {

    public Item toItemWithOwner(final ItemDto itemDto, final long ownerId) {

        if (itemDto == null) {
            return null;
        }

        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .owner(ownerId)
                .build();
    }

    public Item toItem(final ItemDto itemDto) {

        if (itemDto == null) {
            return null;
        }

        return Item.builder()
                .id(itemDto.getId())
                .name(itemDto.getName())
                .description(itemDto.getDescription())
                .available(itemDto.getAvailable())
                .build();
    }

    public ItemDto toItemDto(final Item item) {

        if (item == null) {
            return null;
        }

        return ItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .comments(CommentMapper.toCommentDtoList(item.getComments()))
                .description(item.getDescription())
                .available(item.getAvailable())
                .build();
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

    public static List<ExtendedItemDto> toExtendedItemDtoList(List<Item> itemCollection) {
        List<ExtendedItemDto> extendedItemDtoList = new ArrayList<>();

        for (Item item : itemCollection) {
            extendedItemDtoList.add(ItemMapper.toExtendedItemDto(item));
        }

        return extendedItemDtoList;
    }

    public static ExtendedItemDto toExtendedItemDto(Item item) {
        if (item == null) {
            return null;
        }

        return ExtendedItemDto.builder()
                .id(item.getId())
                .name(item.getName())
                .comments(CommentMapper.toCommentDtoList(item.getComments()))
                .description(item.getDescription())
                .available(item.getAvailable())
                .lastBooking(item.getLastBooking())
                .nextBooking(item.getNextBooking())
                .build();
    }
}
