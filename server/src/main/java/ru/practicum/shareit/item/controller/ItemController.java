package ru.practicum.shareit.item.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ExtendedItemDto;
import ru.practicum.shareit.item.dto.ItemDto;
import ru.practicum.shareit.item.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto create(@RequestBody final ItemDto itemDto,
                          @RequestHeader("X-Sharer-User-Id") final Long userId) {
        log.info("Получен HTTP-запрос по адресу /items (метод POST). Вызван метод create()");
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("{itemId}")
    public ItemDto update(@PathVariable final Long itemId,
                          @RequestBody final ItemDto itemDto,
                          @RequestHeader("X-Sharer-User-Id") final Long userId) {
        log.info("Получен HTTP-запрос по адресу /items/{itemId} (метод PATCH). Вызван метод update()");
        return itemService.update(itemId, itemDto, userId);
    }

    @GetMapping("{itemId}")
    public ExtendedItemDto getItemById(@PathVariable final Long itemId) {
        log.info("Получен HTTP-запрос по адресу /items/{itemId} (метод GET). Вызван метод getItemById()");
        return itemService.getItemById(itemId);
    }

    @GetMapping
    public List<ExtendedItemDto> getUserItems(@RequestHeader("X-Sharer-User-Id") final Long userId) {
        log.info("Получен HTTP-запрос по адресу /items (метод GET). Вызван метод getUserItems()");
        return itemService.getUserItems(userId);
    }

    @GetMapping("search")
    public List<ItemDto> search(@RequestParam("text") final String query) {
        log.info("Получен HTTP-запрос по адресу /items/search (метод GET). Вызван метод getItemsBySearchParam()");
        return itemService.getItemsBySearchParam(query);
    }

    @PostMapping("{itemId}/comment")
    public CommentDto addComment(@PathVariable final Long itemId,
                                 @RequestBody final CommentDto commentDto,
                                 @RequestHeader("X-Sharer-User-Id") final Long userId) {
        return itemService.addComment(itemId, commentDto, userId);
    }
}
