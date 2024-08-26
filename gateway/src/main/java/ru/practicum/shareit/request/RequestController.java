package ru.practicum.shareit.request;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.request.dto.ItemRequestDto;

import java.time.LocalDateTime;

@RestController
@RequestMapping(path = "requests")
@RequiredArgsConstructor
@Slf4j
public class RequestController {
    private final RequestClient requestClient;

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody final ItemRequestDto itemRequestDto,
                                         @RequestHeader("X-Sharer-User-Id") final long userId) {
        log.info("Получен HTTP-запрос по адресу /requests (метод POST). Вызван метод post(itemRequestDto, userId)");
        itemRequestDto.setCreated(LocalDateTime.now());
        return requestClient.post(itemRequestDto, userId);
    }

    @GetMapping
    public ResponseEntity<Object> getOwnRequests(@RequestHeader("X-Sharer-User-Id") final long userId) {
        log.info("Получен HTTP-запрос по адресу /requests (метод GET). Вызван метод get(userId)");
        return requestClient.get(userId);
    }

    @GetMapping("all")
    public ResponseEntity<Object> getAllRequests() {
        log.info("Получен HTTP-запрос по адресу /requests/all (метод GET). Вызван метод get()");
        return requestClient.get();
    }

    @GetMapping("{requestId}")
    public ResponseEntity<Object> getRequestById(@PathVariable final long requestId) {
        log.info("Получен HTTP-запрос по адресу /requests/{requestId} (метод GET). Вызван метод getById(requestId)");
        return requestClient.getById(requestId);
    }
}
