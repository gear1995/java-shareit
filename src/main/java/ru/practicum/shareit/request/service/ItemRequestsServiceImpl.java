package ru.practicum.shareit.request.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.mapper.ItemRequestMapper;
import ru.practicum.shareit.request.model.ItemRequest;
import ru.practicum.shareit.request.repository.AnswerRepository;
import ru.practicum.shareit.request.repository.ItemRequestRepository;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ItemRequestsServiceImpl implements ItemRequestService {
    private final ItemRequestRepository itemRequestRepository;
    private final AnswerRepository answerRepository;
    private final UserRepository userRepository;

    @Override
    public ItemRequestDto create(ItemRequestDto itemRequestDto, Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));
        return ItemRequestMapper.toItemRequestDto(itemRequestRepository.save(ItemRequestMapper.toItemRequest(itemRequestDto)));
    }

    @Override
    public List<ItemRequestDto> getOwnRequests(Long userId) {
        userRepository.findById(userId).orElseThrow(() -> new NotFoundException("User with id " + userId + " not found"));

        return ItemRequestMapper.toItemRequestDtoList(itemRequestRepository.findAllByRequesterId(userId)
                .stream()
                .peek(itemRequest -> itemRequest.setAnswers(answerRepository.findAllByRequestId(itemRequest.getId())))
                .collect(Collectors.toList()));
    }

    @Override
    public List<ItemRequestDto> getAllRequests() {
        return ItemRequestMapper.toItemRequestDtoList(itemRequestRepository.findAllOrderByCreatedDesc());
    }

    @Override
    public ItemRequestDto getRequestById(Long requestId) {
        ItemRequest itemRequest = itemRequestRepository.findById(requestId)
                .orElseThrow(() -> new NotFoundException("Request with id " + requestId + " not found"));
        itemRequest.setAnswers(answerRepository.findAllByRequestId(requestId));

        return ItemRequestMapper.toItemRequestDto(itemRequest);
    }
}
