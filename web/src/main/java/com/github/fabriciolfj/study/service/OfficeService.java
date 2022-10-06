package com.github.fabriciolfj.study.service;

import jooq.generated.tables.Customerdetail;
import jooq.generated.tables.Office;
import jooq.generated.tables.daos.OfficeRepository;
import jooq.generated.tables.pojos.JooqOffice;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jooq.DSLContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.jooq.impl.DSL.row;
import static org.jooq.impl.DSL.select;

@Slf4j
@Service
@RequiredArgsConstructor
public class OfficeService {

    private final OfficeRepository officeRepository;
    private final DSLContext ctx;

    public void testOperatorRow() {
        var result = ctx.selectFrom(Customerdetail.CUSTOMERDETAIL)
                .where(row(Customerdetail.CUSTOMERDETAIL.CITY, Customerdetail.CUSTOMERDETAIL.COUNTRY)
                        .in(select(Office.OFFICE.CITY, Office.OFFICE.COUNTRY).from(Office.OFFICE)));

        result.stream().forEach(
                s -> System.out.println(s.get(Customerdetail.CUSTOMERDETAIL.CITY))
        );
    }

    @Transactional(readOnly = true)
    public List<JooqOffice> getOfficeTerritory(final String territory) {
        return officeRepository.fetchByTerritory(territory);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void addOffice(final JooqOffice office) {
        officeRepository.insert(office);
    }


    public void testDistinct() {
        var result = ctx.selectDistinct(Office.OFFICE.CITY, Office.OFFICE.COUNTRY)
                .from(Office.OFFICE)
                .innerJoin(Customerdetail.CUSTOMERDETAIL)
                .on(Customerdetail.CUSTOMERDETAIL.POSTAL_CODE.ne(Office.OFFICE.POSTAL_CODE))
                .where(Office.OFFICE.CITY.isNotNull())
                .fetch();

        result.stream().forEach(s -> log.info("{} {}", s.get(Office.OFFICE.CITY), s.get(Office.OFFICE.COUNTRY)));
    }

}
