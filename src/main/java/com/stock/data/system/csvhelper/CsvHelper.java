package com.stock.data.system.csvhelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.web.multipart.MultipartFile;

import com.stock.data.system.Entity.StockDataEntity;

public class CsvHelper {

	public static String TYPE = "text/csv";
	static String[] HEADERs = { "Id", "Title", "Description", "Published" };

	public static boolean hasCSVFormat(MultipartFile file) {

		if (!TYPE.equals(file.getContentType())) {
			return false;
		}

		return true;
	}

	public static List<StockDataEntity> csvToStockData(InputStream is) {
		try (BufferedReader fileReader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
				CSVParser csvParser = new CSVParser(fileReader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {

			List<StockDataEntity> stockData = new ArrayList<StockDataEntity>();
            List<String> stockExchange= new ArrayList<>();
			Iterable<CSVRecord> csvRecords = csvParser.getRecords();

			for (CSVRecord csvRecord : csvRecords) {
				StockDataEntity csvstockData = new StockDataEntity();
				csvstockData.setCompanyName(csvRecord.get("companyName"));
				csvstockData.setExchange(csvRecord.get("exchange"));
				csvstockData.setCreatedBy(new Date());
				stockData.add(csvstockData);
				stockExchange.add(csvRecord.get("exchange"));
			}
			if(!stockExchange.contains(stockData.get(0).getExchange())) {
				throw new RuntimeException("Invlid Exchange stock!");
			}

			return stockData;
		} catch (IOException e) {
			throw new RuntimeException("fail to parse CSV file: " + e.getMessage());
		}
	}

}
