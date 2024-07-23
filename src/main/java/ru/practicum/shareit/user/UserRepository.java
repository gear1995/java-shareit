package ru.practicum.shareit.user;

import java.util.List;

public interface UserRepository {

    List<User> findAll();

    User create(User user);

    User update(User newUser, int id);

    User getUserById(int id);

    void delete(int id);
}