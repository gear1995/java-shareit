package ru.practicum.shareit.item.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.item.mapper.ItemMapper;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.model.Item;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {
    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    @Override
    public ItemDto create(ItemDto itemDto, int userId) {
        userRepository.getUserById(userId);
        return ItemMapper.toItemDto(itemRepository.create(ItemMapper.toItemWithOwner(itemDto, userId)));
    }

    @Override
    public ItemDto update(int itemId, ItemDto itemDto, int userId) {
        Item item = itemRepository.getItemById(itemId);
        if (item.getOwner() != userId) {
            throw new NotFoundException("You do not own this item");
        }

        return ItemMapper.toItemDto(itemRepository.update(ItemMapper.toItem(itemDto), itemId));
    }

    @Override
    public ItemDto getItemById(int itemId) {
        return ItemMapper.toItemDto(itemRepository.getItemById(itemId));
    }

    @Override
    public List<ItemDto> getUserItems(int userId) {
        return ItemMapper.toItemDtoList(itemRepository.getUserItems(userId));
    }

    @Override
    public List<ItemDto> getItemsBySearchParam(String query) {
        return ItemMapper.toItemDtoList(itemRepository.getItemsBySearchParam(query));
    }
}
