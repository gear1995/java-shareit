package ru.practicum.shareit.item;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.user.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemDto create(ItemDto itemDto, int userId) {
        userRepository.getUser(userId);
        return ItemMapper.toItemDto(itemRepository.create(itemDto, userId));
    }

    @Override
    public ItemDto update(int itemId, ItemDto itemDto, int userId) {
        Item item = itemRepository.getItemById(itemId);
        if (item.getOwner() != userId) {
            throw new NotFoundException("You do not own this item");
        }
        return ItemMapper.toItemDto(itemRepository.update(itemDto, itemId));
    }

    @Override
    public ItemDto getItemById(int itemId) {
        return ItemMapper.toItemDto(itemRepository.getItemById(itemId));
    }

    @Override
    public List<ItemDto> getUserItems(int userId) {
        List<Item> items = itemRepository.getUserItems(userId);
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (Item item : items) {
            itemDtoList.add(ItemMapper.toItemDto(item));
        }
        return itemDtoList;
    }

    @Override
    public List<ItemDto> getItemsBySearchParam(String query) {
        Set<Item> items = itemRepository.getItemsBySearchParam(query);
        List<ItemDto> itemDtoList = new ArrayList<>();
        for (Item item : items) {
            itemDtoList.add(ItemMapper.toItemDto(item));
        }
        return itemDtoList;
    }
}
