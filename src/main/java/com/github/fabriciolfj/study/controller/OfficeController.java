package com.github.fabriciolfj.study.controller;

import com.github.fabriciolfj.study.pojo.Office;
import com.github.fabriciolfj.study.service.OfficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/offices")
@RequiredArgsConstructor
public class OfficeController {

    private final OfficeService officeService;

    @GetMapping("/{value}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public List<Office> findOfficeTerritory(@PathVariable("value") final String territory) {
        return officeService.getOfficeTerritory(territory);
    }
}
