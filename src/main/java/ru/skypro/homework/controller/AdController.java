package ru.skypro.homework.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.AdInfo;
import ru.skypro.homework.dto.Ads;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads/")
public class AdController {

    @GetMapping
    @Operation(summary = "Gets all the ads.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns ads collection in dto-container",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = Ads.class)
                                    )
                            }
                    )
            })
    public ResponseEntity<Ads>getAll() {
        return new ResponseEntity<>(new Ads(), HttpStatus.OK);
    }

    @PostMapping
    @Operation(summary = "Adds a new ad.",
            responses = {
                    @ApiResponse(
                            responseCode = "201",
                            description = "Returns the created ad's entry",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = Ad.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "In case of unauthorized access"
                    )
            })
    public ResponseEntity<?>create(@RequestBody Ad ad, byte[] image) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @GetMapping("{id}")
    @Operation(summary = "Returns the extended information about the add with pk=id if it exists.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns the found ad's entry with detailed information about the owner",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = AdInfo.class)
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "In case of unauthorized access"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "When not found"
                    )
            })
    public ResponseEntity<?>get(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
