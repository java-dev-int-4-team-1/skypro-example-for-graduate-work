package ru.skypro.homework.dto;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Data
public class UpdateUser {

    @NotNull
    @Size(min = 3, max = 10)
    private String firstName;

    @NotNull
    @Size(min = 3, max = 10)
    private String lastName;

    @Pattern(message = "${validatedValue}",
            regexp = "\\+7\\s?\\(?\\d{3}\\)?\\s?\\d{3}-?\\d{2}-?\\d{2}")
    private String phone;
}
