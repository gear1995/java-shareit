package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto create(UserDto user);

    UserDto update(UserDto updatedUser, int id);

    UserDto getUser(int id);

    void delete(int id);
}
