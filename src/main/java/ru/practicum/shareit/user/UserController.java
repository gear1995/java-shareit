package ru.practicum.shareit.user;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "users")
public class UserController {
    private final UserService userService;

    @GetMapping
    public List<UserDto> findAll() {
        log.info("Получен HTTP-запрос по адресу /users (метод GET). Вызван метод findAll()");
        return userService.findAll();
    }

    @PostMapping
    public UserDto create(@Valid @RequestBody User user) {
        log.info("Получен HTTP-запрос по адресу /users (метод POST)." +
                " Вызван метод create(@Valid @RequestBody UserDto user)");
        return userService.create(user);
    }

    @PatchMapping("{userId}")
    public UserDto update(@Valid @RequestBody UserDto updatedUser, @PathVariable int userId) {
        log.info("Получен HTTP-запрос по адресу /users (метод PUT). "
                + "Вызван метод update(@Valid @RequestBody UserDto newUserDto)");
        return userService.update(updatedUser, userId);
    }

    @DeleteMapping("{userId}")
    public void delete(@PathVariable int userId) {
        log.info("Получен HTTP-запрос по адресу /users/{userId} (метод DELETE). " +
                " Вызван метод delete(@PathVariable int userId)");
        userService.delete(userId);
    }

    @GetMapping("{userId}")
    public UserDto findById(@PathVariable int userId) {
        log.info("Получен HTTP-запрос по адресу /users/{userId} (метод GET). " +
                " Вызван метод getUser(@PathVariable int userId)");
        return userService.getUser(userId);
    }
}
