package com.github.fabriciolfj.study.config;

import com.github.fabriciolfj.study.pojo.SaleStats;
import jooq.generated.tables.Sale;
import lombok.RequiredArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.stream.Collectors;

@Configuration
@RequiredArgsConstructor
public class TestFunctional implements CommandLineRunner {

    private final DSLContext ctx;


    @Override
    public void run(String... args) throws Exception {
      //  somandoViaStream();
    }

    public void somandoViaStream() {
        var result = ctx.select().from(Sale.SALE)
                .stream()
                .collect(
                        Collectors.summarizingDouble(rs -> rs.getValue(Sale.SALE.SALE_)));

        System.out.println(result.getSum());
    }
}
