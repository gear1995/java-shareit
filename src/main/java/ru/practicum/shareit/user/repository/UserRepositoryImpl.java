package ru.practicum.shareit.user.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.model.UserId;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Repository
@RequiredArgsConstructor
@Slf4j
public class UserRepositoryImpl implements UserRepository {
    List<User> users = new ArrayList<>();

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public User create(User newUser) {
        checkEmailExist(newUser.getEmail());
        int userId = UserId.getUserId();
        newUser.setId(userId);
        userId++;
        UserId.setUserId(userId);
        users.add(newUser);

        return newUser;
    }

    @Override
    public User update(User updatedUser, int id) {
        for (User user : users) {
            if (Objects.equals(user.getEmail(), updatedUser.getEmail()) && user.getId() != id) {
                throw new ValidationException("Email already exists");
            }
        }
        User user = getUserById(id);
        if (updatedUser.getName() != null) {
            user.setName(updatedUser.getName());
        }

        if (updatedUser.getEmail() != null) {
            user.setEmail(updatedUser.getEmail());
        }

        return user;
    }

    private void checkEmailExist(String email) {
        for (User user : users) {
            if (Objects.equals(user.getEmail(), email)) {
                throw new ValidationException("Email already exists");
            }
        }
    }

    @Override
    public User getUserById(int id) {
        for (User user : users) {
            if (user.getId() == id) {
                return user;
            }
        }
        throw new NotFoundException("User with id " + id + " not found");
    }

    @Override
    public void delete(int id) {
        users.removeIf(user -> user.getId() == id);
    }
}