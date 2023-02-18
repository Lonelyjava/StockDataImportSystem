package com.stock.data.system.Reposistory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.stock.data.system.Entity.StockDataEntity;

@Repository
public interface StockDataRepo extends JpaRepository<StockDataEntity, Long>{

}
