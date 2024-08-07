package ru.practicum.shareit.user.mapper;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.user.dto.UserDto;
import ru.practicum.shareit.user.model.User;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
@Slf4j
public class UserMapper {
    public UserDto toUserDto(User user) {

        if (user == null) {
            return null;
        }

        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();
    }

    public User toUser(UserDto userDto) {
        if (userDto == null) {
            return null;
        }

        return User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();
    }

    public List<UserDto> toUserDtoList(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (var user : userList) {
            userDtoList.add(UserMapper.toUserDto(user));
        }

        return userDtoList;
    }

    public List<User> toUserList(List<UserDto> userDtoList) {
        List<User> userList = new ArrayList<>();
        for (var user : userDtoList) {
            userList.add(UserMapper.toUser(user));
        }

        return userList;
    }
}
