package ru.practicum.shareit.request.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;
import ru.practicum.shareit.request.service.ItemRequestService;

import java.util.List;

@RestController
@RequestMapping(path = "requests")
@RequiredArgsConstructor
@Slf4j
public class ItemRequestController {
    private final ItemRequestService itemRequestService;

    @PostMapping
    public ItemRequestDto create(@RequestBody final ItemRequestDto itemRequestDto,
                                 @RequestHeader("X-Sharer-User-Id") final Long userId) {
        log.info("Получен HTTP-запрос по адресу /requests (метод POST). Вызван метод create()");
        return itemRequestService.create(itemRequestDto, userId);
    }

    @GetMapping
    public List<ItemRequestDto> getOwnRequests(@RequestHeader("X-Sharer-User-Id") final Long userId) {
        log.info("Получен HTTP-запрос по адресу /requests (метод GET). Вызван метод getOwnRequests()");
        return itemRequestService.getOwnRequests(userId);
    }

    @GetMapping("all")
    public List<ItemRequestDto> getAllRequests() {
        log.info("Получен HTTP-запрос по адресу /requests/all (метод GET). Вызван метод getAllRequests()");
        return itemRequestService.getAllRequests();
    }

    @GetMapping("{requestId}")
    public ItemRequestDto getRequestById(@PathVariable final Long requestId) {
        log.info("Получен HTTP-запрос по адресу /requests/{requestId} (метод GET). Вызван метод getRequestById()");
        return itemRequestService.getRequestById(requestId);
    }
}
