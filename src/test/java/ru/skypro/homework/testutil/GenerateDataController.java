package ru.skypro.homework.testutil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin(value = "${crossorigin.url}")
@RequiredArgsConstructor
@RestController
@RequestMapping("/generate-data")
public class GenerateDataController {

    private final GenerateDataService generateDataService;

    @GetMapping()
    public void get() {
        generateDataService.generateData(10, 40, 100);
    }


}
