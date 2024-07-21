package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> findAll() {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userRepository.findAll()) {
            userDtoList.add(UserMapper.toUserDto(user));
        }
        return userDtoList;
    }

    @Override
    public UserDto create(User user) {
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new ValidationException("Email is required");
        }
        userRepository.create(user);
        return getUser(user.getId());
    }

    @Override
    public UserDto update(UserDto updatedUser, int id) {
        userRepository.update(updatedUser, id);
        return getUser(id);
    }

    @Override
    public UserDto getUser(int id) {
        return UserMapper.toUserDto(userRepository.getUser(id));
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }
}
