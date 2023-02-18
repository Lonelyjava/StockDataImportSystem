package com.stock.data.system.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.stock.data.system.Entity.StockDataEntity;
import com.stock.data.system.csvhelper.CsvHelper;
import com.stock.data.system.response.ResponseMessage;
import com.stock.data.system.service.StockDataService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
public class StockDataController {

	@Autowired
	StockDataService stockDataService;

	@PostMapping("/upload")
	public ResponseEntity<?> getCSVdataSave(@RequestParam("file") MultipartFile file, HttpServletRequest request) {
		final String authorizationHeaderValue = request.getHeader("Authorization");
		if (authorizationHeaderValue != null && authorizationHeaderValue.startsWith("Bearer")) {
			String token = authorizationHeaderValue.substring(7, authorizationHeaderValue.length());
			System.out.println("token " + token);
			String message = "";

			if (CsvHelper.hasCSVFormat(file)) {
				try {
					stockDataService.save(file);

					message = "Uploaded the file successfully: " + file.getOriginalFilename();
					return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
				} catch (Exception e) {
					message = "Could not upload the file: " + file.getOriginalFilename() + "!";
					return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
				}
			}

			message = "Please upload a csv file!";
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));

		} else {
			return new ResponseEntity<>("invalid token or expried token", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/getStockData")
	public ResponseEntity<List<StockDataEntity>> getAllStockData() {
		try {
			List<StockDataEntity> tutorials = stockDataService.getAllStockData();

			if (tutorials.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(tutorials, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
