package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Login;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {




    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody ){
        return null;
    }


    @GetMapping("/me")
    public ResponseEntity<?> getUser(){
        return null;
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody ){
        return null;
    }

    @PatchMapping("/me/image")
    public ResponseEntity<?> updateUserImage(@RequestBody ){
        return null;
    }
}
