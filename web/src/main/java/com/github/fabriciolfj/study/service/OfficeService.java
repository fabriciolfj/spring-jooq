package com.github.fabriciolfj.study.service;

import com.github.fabriciolfj.study.pojo.Office;
import com.github.fabriciolfj.study.repository.OfficeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository repository;

    public List<Office> getOfficeTerritory(final String territory) {
        return repository.findOfficesInTerritory(territory);
    }

}
