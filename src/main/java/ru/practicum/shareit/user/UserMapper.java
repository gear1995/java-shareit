package ru.practicum.shareit.user;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import ru.practicum.shareit.user.dto.UserDto;

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

    public UserDto toUser(UserDto userDto) {
        log.info("Начало преобразования userDto в user");

        if (userDto == null) {
            return null;
        }

        UserDto user = UserDto.builder()
                .id(userDto.getId())
                .name(userDto.getName())
                .email(userDto.getEmail())
                .build();

        log.info("Преобразование userDto в user успешно завершено");

        return user;
    }
}
