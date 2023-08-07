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
import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.Ad;
import ru.skypro.homework.dto.AdInfo;
import ru.skypro.homework.dto.Ads;
import ru.skypro.homework.dto.NewPassword;

import javax.persistence.Lob;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.http.MediaType.APPLICATION_OCTET_STREAM_VALUE;

@Slf4j
@CrossOrigin(value = "http://localhost:3000")
@RequiredArgsConstructor
@RestController
@RequestMapping("/ads/")
public class AdController {

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

    @GetMapping("me")
    @Operation(summary = "Getting all the ads by the authorized user.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns all the entries owned by the authorized user.",
                            content = {
                                    @Content(
                                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                                            schema = @Schema(implementation = Ads.class)
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
    public ResponseEntity<?>getMyAds() {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

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

    @DeleteMapping("{id}")
    @Operation(summary = "Deletes the ad's entry with pk=id if it exists.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Returns if the entry was deleted successfully"
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            description = "No content"
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "In case of unauthorized access"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "When not found"
                    )
            })
    public ResponseEntity<?>delete(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("{id}")
    @Operation(summary = "Renews the ad's entry with pk=id if it exists: set the new title, description and price.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Will be returned if the entry was updated successfully",
                            content = {
                                    @Content (
                                            mediaType = APPLICATION_JSON_VALUE
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "In case of unauthorized access"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found"
                    )
            })
    public ResponseEntity<?>update(@PathVariable Long id, @RequestBody Ad ad) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }

    @PutMapping("{id}/image")
    @Operation(summary = "Renews the ad's image for the ad's entry with pk=id if it exists.",
            operationId = "updateImage",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = @Content(
                            mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
                            schema = @Schema(implementation = MultipartFile.class)
                    )
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Will be returned if the entry was updated successfully",
                            content = {
                                    @Content (
                                            mediaType = APPLICATION_OCTET_STREAM_VALUE
                                    )
                            }
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "In case of unauthorized access"
                    ),
                    @ApiResponse(
                            responseCode = "403",
                            description = "Forbidden"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Not found"
                    )
            })
    public ResponseEntity<?>updateImg(@PathVariable Long id, @RequestBody MultipartFile image) {
        return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
    }
}
