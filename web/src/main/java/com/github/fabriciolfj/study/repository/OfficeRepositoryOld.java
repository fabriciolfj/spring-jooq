package com.github.fabriciolfj.study.repository;

import com.github.fabriciolfj.study.pojo.Office;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static jooq.generated.Tables.OFFICE;


@Repository
@RequiredArgsConstructor
public class OfficeRepositoryOld {

    private final DSLContext ctx;

    @Transactional(readOnly = true)
    public List<Office> findOfficesInTerritory(final String territory) {
        return ctx.select().from(OFFICE)
                .where(OFFICE.TERRITORY.eq(territory))
                .fetchInto(Office.class);
    }

}
