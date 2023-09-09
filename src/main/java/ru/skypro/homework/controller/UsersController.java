package ru.skypro.homework.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.UserDto;
import ru.skypro.homework.service.UserService;

import javax.validation.Valid;
import java.io.IOException;


@Slf4j
@CrossOrigin(value = "${crossorigin.url}")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {

    private final UserService userService;

    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@Valid @RequestBody NewPassword newPassword) {
        if(userService.setPasswordService(newPassword)){
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @GetMapping("/me")
    public UserDto getUser() {
        return userService.getUser();
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUser updateUser) {
        if (userService.updateUser(updateUser)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }

    @PatchMapping(path = "/me/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> updateUserImage(@RequestParam() MultipartFile image)  {
        if (userService.updateImage(image)) {
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
