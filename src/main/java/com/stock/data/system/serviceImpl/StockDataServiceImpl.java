package com.stock.data.system.serviceImpl;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.stock.data.system.Entity.StockDataEntity;
import com.stock.data.system.Reposistory.StockDataRepo;
import com.stock.data.system.csvhelper.CsvHelper;
import com.stock.data.system.service.StockDataService;

@Service
public class StockDataServiceImpl implements StockDataService {

	@Autowired
	StockDataRepo dataRepo;

	@Override
	public void save(MultipartFile file) {
		try {
			List<StockDataEntity> tutorials = CsvHelper.csvToStockData(file.getInputStream());
			dataRepo.saveAll(tutorials);
		} catch (IOException e) {
			throw new RuntimeException("fail to store csv data: " + e.getMessage());
		}
	}

	@Override
	public List<StockDataEntity> getAllStockData() {
		return dataRepo.findAll();
	}
}
