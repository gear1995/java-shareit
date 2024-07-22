package ru.practicum.shareit.user;

import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User create(UserDto userDto);

    User update(UserDto newUser, int id);

    User getUser(int id);

    void delete(int id);
}