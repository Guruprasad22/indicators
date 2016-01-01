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
		 String tableExists = (String) sqlMap.queryForObject("findTable","ticker");

		 if(tableExists == null) {
			 log.info("table " + " TICKER" + " does not exist");
			 sqlMap.update("createTable");
		 } else {
			 log.info("table " + " TICKER" + " exists");
		 }
		 
		 // create file table if it does not exist
		 tableExists = (String) sqlMap.queryForObject("findTable","file");	 

		 if(tableExists == null) {
			 log.info("table " + " FILE" + " does not exist");
			 sqlMap.update("createTable_File");
		 } else {
			 log.info("table " + " FILE" + "exists ");
		 }
	}
	
	/*
	 * check if files are already committed to database
	 */
	public List<File> doValidation() throws Exception {
		log.debug("++++doValidation");
		//load the directory path from properties file
		Properties properties = new Properties();
		properties.load(DatabaseService.class.getClassLoader().getResourceAsStream("config.properties"));
		log.debug("Input directory is set to : " + properties.getProperty("inputDir"));
		/*try (final InputStream stream =
		           this.getClass().getResourceAsStream("foo.properties")) {
			properties.load(stream);
		}*/
		 // read all the ticker files already inserted into database
		 List<String> tickerFiles =  (List<String>) sqlMap.queryForList("getFilenames");
		 
		 if(tickerFiles.isEmpty()) {
			 log.info("There are no files committed to database");
		 }
		 else {
			 log.info("The files committed to database are ");
			 for(String fileName : tickerFiles) {
				 log.info(fileName);
			 }
		 }
		 
		 List<File> filteredFiles = new ArrayList<File>();
		 FileReaderService fileReaderService = new FileReaderService();
		 fileReaderService.setDirectoryName(properties.getProperty("inputDir"));
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
		log.debug("++++commitRecords");
		FileReaderService reader =  new FileReaderService();		
    	List<File> files = doValidation();
    	List<Ticker> tickers = reader.readDataFiles(files); //inserting only the delta files from source directory
    	log.debug("ticker size is " + tickers.size());
	
		long startTime  = System.currentTimeMillis();
		//insert tickers into table ticker
		sqlMap.startTransaction();
		sqlMap.startBatch();
		
		for(Ticker ticker: tickers) {
			sqlMap.insert("insertTicker",ticker);
		}	
		
		sqlMap.executeBatch();
		sqlMap.commitTransaction();
		
		//insert the filenames into table files
		sqlMap.startTransaction();
		sqlMap.startBatch();
		 
		for(File f: files) {
		 log.info("Inserting file " + f.getName() + " into database ");
		 sqlMap.insert("insertFile",new String(f.getName()));
		}
		 
		sqlMap.executeBatch();
		sqlMap.commitTransaction();
		 
		long endTime  = System.currentTimeMillis();
		log.info("Total time taken = " + (endTime- startTime)/1000 + " seconds");
		log.debug("++++commitRecords");
	}
	
	public void commitIndicator(List<Indicator> myList) throws SQLException {
		
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
		log.info("++++++commitIndicator");
	}
	
	public List<Ticker> getAllTickers() throws SQLException {
		// read all the tickers from database
		List<Ticker> tickerList =  (List<Ticker>) sqlMap.queryForList("getTickers");
				 
		if(tickerList.isEmpty()) {
			log.info("There are no tickers committed to database");
		}else{
			log.debug("The files committed to database are ");
					 for(Ticker ticker : tickerList) {
						 log.debug(ticker);
					 }
				 }
		return tickerList;
	}
}
