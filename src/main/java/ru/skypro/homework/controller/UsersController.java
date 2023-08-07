package ru.skypro.homework.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.NewPassword;
import ru.skypro.homework.dto.Register;
import ru.skypro.homework.dto.UpdateUser;


@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UsersController {


    @PostMapping("/set_password")
    public ResponseEntity<?> setPassword(@RequestBody NewPassword newPassword){
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @GetMapping("/me")
    public ResponseEntity<Register> getUser(){
        return ResponseEntity.ok(new Register());
    }

    @PatchMapping("/me")
    public ResponseEntity<?> updateUser(@RequestBody UpdateUser updateUser){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/me/image")
    public ResponseEntity<?> updateUserImage(@RequestBody byte[] image){
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
