package ru.practicum.shareit.user;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.user.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@UtilityClass
@Slf4j
public class UserMapper {
    public UserDto toUserDto(User user) {
        log.info("Начало преобразования user в userDto");

        if (user == null) {
            return null;
        }

        UserDto userDto = UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();

        log.info("Преобразование user в userDto успешно завершено");

        return userDto;
    }

    public User toUser(UserDto userDto) {
        log.info("Начало преобразования userDto в user");

        if (userDto == null) {
            return null;
        }

        User user = User.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();

        log.info("Преобразование userDto в user успешно завершено");

        return user;
    }

    public List<UserDto> toUserDtoList(List<User> userList) {
        List<UserDto> userDtoList = new ArrayList<>();
        for (User user : userList) {
            userDtoList.add(UserMapper.toUserDto(user));
        }
        return userDtoList;
    }

    public List<User> toUserList(List<UserDto> userDtoList) {
        List<User> userList = new ArrayList<>();
        for (UserDto user : userDtoList) {
            userList.add(UserMapper.toUser(user));
        }
        return userList;
    }
}
