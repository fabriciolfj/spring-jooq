package com.github.fabriciolfj.study.config;

import jooq.generated.tables.Manager;
import org.jooq.DSLContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TestInsertDefault implements CommandLineRunner {

    @Autowired
    private DSLContext context;

    @Override
    public void run(String... args) throws Exception {
        context.insertInto(Manager.MANAGER).defaultValues().execute();
    }
}
