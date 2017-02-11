package com.playground.service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;
import com.playground.model.Derivative;
import com.playground.model.FileMap;
import com.playground.model.Indicator;
import com.playground.model.Ticker;


public class DatabaseService {
	
	private final String resource = "SqlMapConfig.xml";
	private SqlMapClient sqlMap;
	private Reader reader;
	private static Logger log = Logger.getLogger(DatabaseService.class);
	
	public DatabaseService() throws IOException {
		setUp();
	}
	
	public void setUp() throws IOException {		
		reader = Resources.getResourceAsReader(resource);
		sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);		
	}
	
	public void createDatabaseTables() throws SQLException{
		 //create ticker table if it does not exist
		 String tableExists = (String) sqlMap.queryForObject("findTable",new String("ticker"));

		 if(tableExists == null) {
			 log.info("table " + " TICKER" + " does not exist");
			 sqlMap.update("createTable");
		 } else {
			 log.info("table " + " TICKER " + " exists");
		 }
		 
		 // create file table if it does not exist
		 tableExists = (String) sqlMap.queryForObject("findTable",new String("file"));	 

		 if(tableExists == null) {
			 log.info("table " + " FILE" + " does not exist");
			 sqlMap.update("createTable_File");
		 } else {
			 log.info("table " + " FILE " + "exists ");
		 }
		 
		 //create derivative table if it does not exist
		 tableExists = (String) sqlMap.queryForObject("findTable",new String("derivative"));
		 if(tableExists == null) {
			 log.info("table " + " derivative" + " does not exist");
			 sqlMap.update("createTable_Derivative");
		 } else {
			 log.info("table " + " derivative " + "exists ");
		 }
		 
		 //create table indiactor if it deos not exist
		 tableExists = (String) sqlMap.queryForObject("findTable",new String("indicator"));
		 if(tableExists == null) {
			 log.info("table " + "  indicator" + " does not exist");
			 sqlMap.update("createIndicator");
		 } else {
			 log.info("table " + " indicator " + "exists ");
		 }		 
	}
	
	/*
	 * check if files are already committed to database
	 */
	public List<File> doValidation(String choice) throws Exception {
		log.debug("++++doValidation");
		//load the directory path from properties file
		Properties properties = new Properties();
		properties.load(DatabaseService.class.getClassLoader().getResourceAsStream("config.properties"));
		log.debug("Input directory is set to : " + properties.getProperty("inputDir"));
		 // read all the ticker files already inserted into database
		 List<String> tickerFiles =  (List<String>) sqlMap.queryForList("getFilenames",new String(choice));
		 
		 if(tickerFiles.isEmpty()) {
			 log.info("There are no files committed to database");
		 }
		 else {
			 log.debug("The files committed to database are ");
			 for(String fileName : tickerFiles) {
				 log.debug(fileName);
			 }
		 }
		 
		 List<File> filteredFiles = new ArrayList<File>();
		 FileReaderService fileReaderService = new FileReaderService();
		 if(choice.equalsIgnoreCase("N")) {
			 fileReaderService.setDirectoryName(properties.getProperty("openInterestDir"));
		 } else {
			 fileReaderService.setDirectoryName(properties.getProperty("inputDir"));
		 }
		 List<File> inputFiles = fileReaderService.loadDataFiles();
		 for(File f : inputFiles) {
			 if(!tickerFiles.contains(f.getName())) {
				 log.debug(f);
				 filteredFiles.add(f);
			 }
		 }
		 log.debug("----doValidation");
		 return filteredFiles;
	}
	
	/**
	 * the method checks a given directory for list of files
	 * it removes files that are already committed to database
	 * then converts them to POJOs and invokes the insert command through ibatis
	 * @throws Exception
	 */
	public void commitRecords() throws Exception {
		try {
			
			log.info("----commitRecords");
			FileReaderService reader =  new FileReaderService();		
			List<File> files = doValidation("Y");
			List<Ticker> tickers = new ArrayList<Ticker>();
			if( files.size() > 0 ) {
				 tickers = reader.readDataFiles(files); //inserting only the delta files from source directory
			}
			
			long startTime  = System.currentTimeMillis();
			//insert tickers into table ticker
			sqlMap.startTransaction();
			sqlMap.startBatch();
			
			for(Ticker ticker: tickers) {
//			log.debug(ticker);
				sqlMap.insert("insertTicker",ticker);
			}	
			
			sqlMap.executeBatch();
			sqlMap.commitTransaction();
			
			commitFileNames(files,"Y");
			
			long endTime  = System.currentTimeMillis();
			log.info("Total time taken for commitRecords = " + (endTime- startTime)/1000 + " seconds");
			log.debug("++++commitRecords");
		}catch(Exception e) {
			log.debug(e);
		}
	}
	
	public void commitIndicator(List<Indicator> myList) throws Exception {
		try {
			log.info("-----commitIndicator");
			long startTime  = System.currentTimeMillis();
			
			sqlMap.startTransaction();
			sqlMap.startBatch();
			for(Indicator ind: myList) {
				sqlMap.insert("insertIndicator",ind);
			}
			sqlMap.executeBatch();
			sqlMap.commitTransaction();
			long endTime = System.currentTimeMillis();
			log.info("Total time taken to commit indicators : " + (endTime - startTime)/(1000*60) + " minutes.");
			log.info("+++++commitIndicator");
		}catch(Exception e) {
			log.debug(e);
		}
	}
	
	public void truncateIndicator() throws Exception {
		try {
			log.info("-----truncateIndicator");
			long startTime  = System.currentTimeMillis();
			
			sqlMap.startTransaction();
			sqlMap.startBatch();
			sqlMap.delete("deleteIndicators");
			sqlMap.executeBatch();
			sqlMap.commitTransaction();
			long endTime = System.currentTimeMillis();
			log.info("Total time taken to commit indicators : " + (endTime - startTime)/(1000*60) + " minutes");
			log.info("+++++truncateIndicator");
		}catch(Exception e) {
			log.debug(e);
		}
	}
	
	public void commitDerivatives(List<Derivative> myList) throws Exception {
		try {
			log.info("-----commitDerivatives-----");
			long startTime  = System.currentTimeMillis();
			sqlMap.startTransaction();
			sqlMap.startBatch();
			for(Derivative der: myList) {
				sqlMap.insert("insertDerivative",der);
			}
			sqlMap.executeBatch();
			sqlMap.commitTransaction();
			long endTime = System.currentTimeMillis();
			log.debug("Total time taken to commit derivatives : " + (endTime - startTime)/(1000) + " seconds.");
			log.info("++++++commitDerivatives+++++");
		}catch(Exception e) {
			log.debug(e);
		}
	}
	
	public void commitFileNames(List<File> files,String choice) {
		try {
			//insert the filenames into table files
			log.info("----commitFileNames");
			sqlMap.startTransaction();
			sqlMap.startBatch();
			 
			for(File f: files) {
			 log.info("Inserting file " + f.getName() + " into database ");
			 FileMap fileMap = new FileMap();
			 fileMap.setName(f.getName());
			 fileMap.setTicker(choice);
			 sqlMap.insert("insertFile",fileMap);
			}
			 
			sqlMap.executeBatch();
			sqlMap.commitTransaction();
			log.info("++++commitFileNames");
		}catch(Exception e) {
			log.debug(e);
		}
		
	}
	
	public List<Ticker> getAllTickers() throws SQLException {
		// read all the tickers from database
		List<Ticker> tickerList =  (List<Ticker>) sqlMap.queryForList("getTickers");
		if(tickerList.isEmpty()) {
			log.info("There are no tickers committed to database");
		}else{
			log.debug("The files committed to database are ");
					 for(Ticker ticker : tickerList) {
//						 log.debug(ticker);
					 }
				 }
		return tickerList;
	}
	
	public List<Ticker> getAllEquityTickers() throws SQLException {
		// read all the tickers from database
		log.info("----getAllEquityTickers");
		List<Ticker> tickerList =  (List<Ticker>) sqlMap.queryForList("getEquityTickers");
		if(tickerList.isEmpty()) {
			log.info("There are no tickers committed to database");
		}else{
//			log.debug("The files committed to database are ");
//					 for(Ticker ticker : tickerList) {
//						 log.debug(ticker);
//					 }
				 }
		log.info("++++getAllEquityTickers");
		return tickerList;
	}
}
