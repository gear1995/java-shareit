package ru.practicum.shareit.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public List<UserDto> findAll() {
        return UserMapper.toUserDtoList(userRepository.findAll());
    }

    @Override
    public UserDto create(UserDto userDto) {
        return UserMapper.toUserDto(userRepository.create(UserMapper.toUser(userDto)));
    }

    @Override
    public UserDto update(UserDto updatedUser, int id) {
        return UserMapper.toUserDto(userRepository.update(UserMapper.toUser(updatedUser), id));
    }

    @Override
    public UserDto getUserById(int id) {
        return UserMapper.toUserDto(userRepository.getUserById(id));
    }

    @Override
    public void delete(int id) {
        userRepository.delete(id);
    }
}
