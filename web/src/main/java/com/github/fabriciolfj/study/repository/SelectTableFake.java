package com.github.fabriciolfj.study.repository;

import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import static org.jooq.impl.DSL.*;

//@Component
public class SelectTableFake {

    //@Autowired
    private DSLContext context;

    public void queryExample() {
        var query = context.select(val(1).as("A")).fetch();

    }
}
