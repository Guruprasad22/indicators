package com.playground.testsuite;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.playground.model.Indicator;
import com.playground.model.Ticker;
import com.playground.service.Ad;
import com.playground.service.DatabaseService;
import com.playground.service.FileReaderService;
import com.playground.service.ForceIndex;
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
	public void calculateIndicators() throws Exception {
		SimpleMovingAverage simpleMovingAverage = new SimpleMovingAverage();
		simpleMovingAverage.compileMapOfIndividualStocks();
		Map<String,ArrayList<Indicator>> myMap = simpleMovingAverage.calculateSma(12);
//		MapUtil.printMap(myMap);
		/*Ema12 ema = new Ema12();
		ema.setIndicatorMap(myMap);
		myMap = ema.doEma();
		Ema26 ema26 = new Ema26();
		ema26.setIndicatorMap(myMap);
		myMap = ema26.doEma();
		Macd macd = new Macd();
		macd.setIndicatorMap(myMap);
		myMap = macd.doMacd();*/
		/*Adx adx = new Adx();
		adx.setIndicatorMap(myMap);
		myMap = adx.doAdx();
		Rsi rsi = new Rsi();
		rsi.setIndicatorMap(myMap);
		myMap = rsi.doRsi();
		Obv obv = new Obv();
		obv.setIndicatorMap(myMap);
		myMap = obv.doObv();
		Ad ad = new Ad();
		ad.setIndicatorMap(myMap);
		myMap = ad.doAccumulationDistribution();*/
		ForceIndex forceIndex = new ForceIndex();
		forceIndex.setIndicatorMap(myMap);
		myMap = forceIndex.doForceIndex();
		ArrayList<Indicator> list = MapUtil.compileList(myMap);
		MapUtil.printMap(myMap);
		new DatabaseService().commitIndicator(list);
	}
}
