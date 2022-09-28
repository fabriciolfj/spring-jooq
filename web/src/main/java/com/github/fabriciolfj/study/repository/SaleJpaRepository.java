package com.github.fabriciolfj.study.repository;

import com.github.fabriciolfj.entities.Sale;
import com.github.fabriciolfj.study.repository.SaleRepository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleJpaRepository extends JpaRepository<Sale, Long>, SaleRepository {

    List<Sale> findTop10By();
}
