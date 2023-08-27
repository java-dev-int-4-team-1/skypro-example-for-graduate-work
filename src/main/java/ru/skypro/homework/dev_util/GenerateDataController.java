package ru.skypro.homework.dev_util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(value = "${cross-origin.value}")
@RequiredArgsConstructor
@RestController
@RequestMapping("/generate-data")
public class GenerateDataController {

    private final GenerateDataService genarateDataService;

    @GetMapping()
    public void get() {
        genarateDataService.generateData(10, 40, 100);
    }


}
