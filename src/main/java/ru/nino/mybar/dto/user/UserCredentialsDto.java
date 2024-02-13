package ru.nino.mybar.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserCredentialsDto {

    @NotBlank
    @Schema(description = "Пользовательское имя ", example = "sozanovOleg123")
    private String name;

    @NotBlank
    @Schema(description = "Пользовательский пароль", example = "sozanovOleg123")
    private String password;

}
