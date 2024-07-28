package ru.practicum.shareit.user.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(of = {"id"})
@Builder
public class UserDto {
    private int id;
    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Pattern(regexp = "^[A-Za-z0-9]*$", message = "имя пользователя должно быть без специальных символов и пробелов")
    private String name;
}
