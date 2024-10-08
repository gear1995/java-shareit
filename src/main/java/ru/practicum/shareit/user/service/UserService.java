package ru.practicum.shareit.user.service;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserService {
    List<UserDto> findAll();

    UserDto create(UserDto user);

    UserDto update(UserDto updatedUser, Long userId);

    UserDto getUserById(Long userId);

    void delete(Long userId);
}
