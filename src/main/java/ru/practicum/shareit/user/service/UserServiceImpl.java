package ru.practicum.shareit.user.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.practicum.shareit.exeption.NotFoundException;
import ru.practicum.shareit.exeption.ValidationException;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.mapper.UserMapper;
import ru.practicum.shareit.user.model.User;
import ru.practicum.shareit.user.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> findAll() {
        return UserMapper.toUserDtoList(userRepository.findAll());
    }

    @Override
    @Transactional
    public UserDto create(UserDto userDto) {
        return UserMapper.toUserDto(userRepository.save(UserMapper.toUser(userDto)));
    }

    @Override
    @Transactional
    public UserDto update(UserDto updatedUser, Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (updatedUser.getName() != null) {
            user.setName(updatedUser.getName());
        }

        if (updatedUser.getEmail() != null) {
            userRepository.findByEmail(updatedUser.getEmail()).ifPresent(userSameEmail -> {
                if (!user.equals(userSameEmail)) {
                    throw new ValidationException("Email already exists");
                }
            });
            user.setEmail(updatedUser.getEmail());
        }

        return UserMapper.toUserDto(userRepository.save(user));

    }

    @Override
    public UserDto getUserById(Long userId) {
        return UserMapper.toUserDto(userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User with id " + userId + " not found")));
    }

    @Override
    @Transactional
    public void delete(Long userId) {
        userRepository.deleteById(userId);
    }
}
