package com.playground.testsuite;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.junit.Test;

import com.playground.model.Derivative;
import com.playground.model.Indicator;
import com.playground.model.Ticker;
import com.playground.service.Ad;
import com.playground.service.Adx;
import com.playground.service.DatabaseService;
import com.playground.service.Ema12;
import com.playground.service.Ema26;
import com.playground.service.FileReaderService;
import com.playground.service.ForceIndex;
import com.playground.service.Macd;
import com.playground.service.Obv;
import com.playground.service.Rsi;
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
//    		System.out.println(t);
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
		dbService.doValidation("Y");
	}
	
	@Test
	public void commitRecords() throws Exception {
		//this method looks for any new files that get added under bhav folder and commits them to ticker table
		DatabaseService databaseService =  new DatabaseService();
		databaseService.commitRecords();		
	}
//	@Test
	public void getTickers() throws Exception {
		DatabaseService databaseService = new DatabaseService();
		databaseService.getAllTickers();
	}
	
//	@Test
	public void truncateIndicators() throws Exception {
		DatabaseService databaseService =  new DatabaseService();
		databaseService.truncateIndicator();
	}
	
	@Test
	public void calculateIndicators() throws Exception {
		//first truncate the indicator table and then start with the calculations
		DatabaseService databaseService =  new DatabaseService();
		databaseService.truncateIndicator();
		// compile the list of all tickers
		SimpleMovingAverage simpleMovingAverage = new SimpleMovingAverage();
		simpleMovingAverage.compileMapOfIndividualStocks();
		// sort and calculate the 12 day sma
		Map<String,ArrayList<Indicator>> myMap = simpleMovingAverage.calculateSma(12);
//		MapUtil.printMap(myMap);
		Ema12 ema = new Ema12();
		ema.setIndicatorMap(myMap);
		myMap = ema.doEma();
		Ema26 ema26 = new Ema26();
		ema26.setIndicatorMap(myMap);
		myMap = ema26.doEma();
		Macd macd = new Macd();
		macd.setIndicatorMap(myMap);
		myMap = macd.doMacd();
		ForceIndex forceIndex = new ForceIndex();
		forceIndex.setIndicatorMap(myMap);
		myMap = forceIndex.getForceIndex();
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
		ArrayList<Indicator> list = MapUtil.compileList(myMap);
//		MapUtil.printMap(myMap);
		new DatabaseService().commitIndicator1(list);
	}
	
//	@Test
	public void readAndCommitOpenInterest() throws Exception {
		FileReaderService fileReaderService = new FileReaderService();
		Properties properties = new Properties();
		properties.load(IndicatorTest.class.getClassLoader().getResourceAsStream("config.properties"));
		fileReaderService.setDirectoryName(properties.getProperty("openInterestDir"));
		List<File> files = fileReaderService.loadDataFiles();
		files = new DatabaseService().doValidation("N");
		List<Derivative> myList = fileReaderService.readOIFiles(files);
		System.out.println("list size is " + myList.size());
		new DatabaseService().commitDerivatives(myList);
		new DatabaseService().commitFileNames(files, "N");
	}
}
