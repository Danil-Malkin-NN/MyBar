package ru.nino.mybar.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserRegistryDTO {

    @Email
    @NotBlank
    @Schema(description = "Почта пользователя", example = "yourMail@gmail.com")
    private String email;

    @Schema(description = "Имя пользователя", example = "Олег")
    private String firstName = "";

    @Schema(description = "Фамилия", example = "Созанов")
    private String lastName = "";

    private UserCredentialsDto userCredentialsDto;


}
