package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;

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
    public User create(UserDto newUser) {
        checkEmailExist(newUser.getEmail());
        int userId = UserId.getUserId();
        newUser.setId(userId);
        userId++;
        UserId.setUserId(userId);
        User user = UserMapper.toUser(newUser);
        users.add(user);
        return user;
    }

    @Override
    public User update(UserDto updatedUser, int id) {
        for (User user : users) {
            if (Objects.equals(user.getEmail(), updatedUser.getEmail()) && user.getId() != id) {
                throw new ValidationException("Email already exists");
            }
        }
        User user = getUser(id);
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
    public User getUser(int id) {
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