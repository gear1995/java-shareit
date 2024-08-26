package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.CommentDto;
import ru.practicum.shareit.item.dto.ItemDto;

@RestController
@RequestMapping("items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemClient itemClient;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody final ItemDto itemDto,
                                         @RequestHeader("X-Sharer-User-Id") final long userId) {
        log.info("Получен HTTP-запрос по адресу /items (метод POST). Вызван метод createItem()");
        return itemClient.createItem(itemDto, userId);
    }

    @PatchMapping("{itemId}")
    public ResponseEntity<Object> update(@PathVariable final long itemId,
                                         @RequestHeader("X-Sharer-User-Id") final long userId,
                                         @RequestBody final ItemDto itemDto) {
        log.info("Получен HTTP-запрос по адресу /items/{itemId} (метод PATCH). Вызван метод updateItemById()");
        return itemClient.updateItemById(itemId, userId, itemDto);
    }

    @GetMapping("{itemId}")
    public ResponseEntity<Object> getItemById(@PathVariable final long itemId) {
        log.info("Получен HTTP-запрос по адресу /items/{itemId} (метод GET). Вызван метод getItemById()");
        return itemClient.getItemById(itemId);
    }

    @GetMapping
    public ResponseEntity<Object> getUserItems(@RequestHeader("X-Sharer-User-Id") final long userId) {
        log.info("Получен HTTP-запрос по адресу /items (метод GET). Вызван метод getUserItems()");
        return itemClient.getUserItems(userId);
    }

    @GetMapping("search")
    public ResponseEntity<Object> search(@RequestParam(value = "text") final String query) {
        log.info("Получен HTTP-запрос по адресу /items/search (метод GET). Вызван метод getItemsBySearchParam()");
        return itemClient.getItemsBySearchParam(query);
    }

    @PostMapping("{itemId}/comment")
    public ResponseEntity<Object> addComment(@PathVariable final long itemId,
                                             @RequestBody final CommentDto commentDto,
                                             @RequestHeader("X-Sharer-User-Id") final long userId) {
        return itemClient.addComment(itemId, commentDto, userId);
    }
}
