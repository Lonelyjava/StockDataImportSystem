package com.stock.data.system.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.stock.data.system.Entity.StockDataEntity;

public interface StockDataService {

	public void save(MultipartFile file);

	public List<StockDataEntity> getAllStockData();

}
