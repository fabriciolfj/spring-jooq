package com.github.fabriciolfj.study.controller;

import com.github.fabriciolfj.study.service.OfficeService;
import com.github.fabriciolfj.study.pojo.Office;
import jooq.generated.tables.pojos.JooqOffice;
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
    public List<JooqOffice> findOfficeTerritory(@PathVariable("value") final String territory) {
        return officeService.getOfficeTerritory(territory);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void create(@RequestBody final JooqOffice office) {
        officeService.addOffice(office);
    }
}
