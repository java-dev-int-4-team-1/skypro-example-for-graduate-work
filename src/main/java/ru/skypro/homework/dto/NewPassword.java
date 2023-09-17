package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class NewPassword {

    @NotNull
    @Size(min = 8, max = 16)
    private String currentPassword;

    @NotNull
    @Size(min = 8, max = 16)
    private String newPassword;

}
