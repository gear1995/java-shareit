package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("users")
public class UserController {
    private final UserClient userClient;

    @GetMapping
    public ResponseEntity<Object> findAll() {
        log.info("Получен HTTP-запрос по адресу /users (метод GET). Вызван метод findAll()");
        return userClient.findAll();
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody UserDto userDto) {
        log.info("Получен HTTP-запрос по адресу /users (метод POST)." +
                 " Вызван метод create(user)");
        return userClient.create(userDto);
    }

    @PatchMapping("{userId}")
    public ResponseEntity<Object> update(@RequestBody UserDto updatedUser, @PathVariable long userId) {
        log.info("Получен HTTP-запрос по адресу /users (метод PUT). "
                 + "Вызван метод update(updatedUser, userId)");
        return userClient.update(updatedUser, userId);
    }

    @DeleteMapping("{userId}")
    public ResponseEntity<Object> delete(@PathVariable long userId) {
        log.info("Получен HTTP-запрос по адресу /users/{userId} (метод DELETE). " +
                 " Вызван метод delete(userId)");
        return userClient.delete(userId);
    }

    @GetMapping("{userId}")
    public ResponseEntity<Object> getUserById(@PathVariable long userId) {
        log.info("Получен HTTP-запрос по адресу /users/{userId} (метод GET). " +
                 " Вызван метод get(userId)");
        return userClient.get(userId);
    }
}
