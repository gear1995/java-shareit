package ru.practicum.shareit.item;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.item.dto.ItemDto;

import java.util.List;

@RestController
@RequestMapping("items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;

    @PostMapping
    public ItemDto create(@Valid @RequestBody final ItemDto itemDto,
                          @RequestHeader("X-Sharer-User-Id") final int userId) {
        log.info("Получен HTTP-запрос по адресу /items (метод POST). Вызван метод create()");
        return itemService.create(itemDto, userId);
    }

    @PatchMapping("{itemId}")
    public ItemDto update(@PathVariable final int itemId,
                          @RequestBody final ItemDto itemDto,
                          @RequestHeader("X-Sharer-User-Id") final int userId) {
        log.info("Получен HTTP-запрос по адресу /items/{itemId} (метод PATCH). Вызван метод update()");
        return itemService.update(itemId, itemDto, userId);
    }

    @GetMapping("{itemId}")
    public ItemDto getItemById(@PathVariable final int itemId) {
        log.info("Получен HTTP-запрос по адресу /items/{itemId} (метод GET). Вызван метод getItemById()");
        return itemService.getItemById(itemId);
    }

    @GetMapping
    public List<ItemDto> getUserItems(@RequestHeader("X-Sharer-User-Id") final int userId) {
        log.info("Получен HTTP-запрос по адресу /items (метод GET). Вызван метод getUserItems()");
        return itemService.getUserItems(userId);
    }

    @GetMapping("search")
    public List<ItemDto> search(@RequestParam("text") final String query) {
        log.info("Получен HTTP-запрос по адресу /items/search (метод GET). Вызван метод getItemsBySearchParam()");
        return itemService.getItemsBySearchParam(query);
    }
}
