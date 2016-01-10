package com.playground.testsuite;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.junit.Test;

import com.playground.model.Indicator;
import com.playground.model.Ticker;
import com.playground.service.DatabaseService;
import com.playground.service.Ema;
import com.playground.service.FileReaderService;
import com.playground.service.SimpleMovingAverage;
import com.playground.utility.MapUtil;
/**
 * Unit test for simple App.
 */
public class IndicatorTest {

    /*
     * test if we can get the correct list of files
     */
	
//	@Test
    public void doFilesTest() throws Exception {
		FileReaderService reader = new FileReaderService();
    	reader.setDirectoryName("C:\\Vault\\bhav");
    	List<File> files = reader.loadDataFiles();
    	
    	for(File file: files) {
    		System.out.println(file.toString());
    	}    	
    }
	
//	@Test
	public void doReadFilesTest() throws Exception {
		FileReaderService reader =  new FileReaderService();
    	reader.setDirectoryName("C:\\Vault\\bhav");
    	List<File> files = reader.loadDataFiles();
    	List<Ticker> tickers = reader.readDataFiles(files);
    	for(Ticker t : tickers) {
    		System.out.println(t);
    	}
	}
	
//	@Test
	public void doDBTest() throws IOException, SQLException {
		DatabaseService dbService = new DatabaseService();
		dbService.setUp();
		dbService.createDatabaseTables();
	}
	
//	@Test
	public void doDataValidation() throws Exception {
		
		DatabaseService dbService = new DatabaseService();
		dbService.doValidation();
	}
	
//	@Test
	public void commitRecords() throws Exception {
		
		DatabaseService databaseService =  new DatabaseService();
		databaseService.commitRecords();		
	}
//	@Test
	public void getTickers() throws Exception {
		DatabaseService databaseService = new DatabaseService();
		databaseService.getAllTickers();
	}
	@Test
	public void getTickersMap() throws Exception {
		SimpleMovingAverage simpleMovingAverage = new SimpleMovingAverage();
		simpleMovingAverage.compileMapOfIndividualStocks();
		Map<String,ArrayList<Indicator>> myMap = simpleMovingAverage.calculateSma(12);
		MapUtil.printMap(myMap);
		Ema ema = new Ema();
		ema.setIndicatorMap(myMap);
		myMap = ema.doEma();
		ArrayList<Indicator> list = MapUtil.compileList(myMap);
//		MapUtil.printMap(myMap);
		new DatabaseService().commitIndicator(list);
	}
}
