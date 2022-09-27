package com.github.fabriciolfj.study.service;

import jooq.generated.tables.daos.OfficeRepository;
import jooq.generated.tables.pojos.JooqOffice;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;

    @Transactional(readOnly = true)
    public List<JooqOffice> getOfficeTerritory(final String territory) {
        return officeRepository.fetchByTerritory(territory);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addOffice(final JooqOffice office) {
        officeRepository.insert(office);
    }

}
