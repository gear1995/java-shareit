package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.item.repository.ItemRepository;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemRequestsServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final UserRepository userRepository;
    private final ItemRepository itemRepository;

    @Override
    public ItemRequestDto create(ItemRequestDto itemRequestDto, long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
        itemRequestDto.setRequesterId(userId);
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.save(ItemRequestMapper.toItemRequest(itemRequestDto)));
    }

    @Override
    public List<ItemRequestDto> getOwnRequests(long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));

        return ItemRequestMapper.toItemRequestDtoList(itemRequestRepository.findAllByRequesterId(userId));
    }

    @Override
    public List<ItemRequestDto> getAllRequests() {
        return ItemRequestMapper.toItemRequestDtoList(itemRequestRepository.findAllOrderByCreatedDesc());
    }

    @Override
    public ItemRequestDto getRequestById(long requestId, long userId) {
        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request with id " + requestId + " not found"));
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
        itemRequest.setItems(itemRepository.findByRequestId(requestId));

        return ItemRequestMapper.toItemRequestDto(itemRequest);
    }
}
