package com.playground.testsuite;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.junit.Test;

import com.playground.model.Ticker;
import com.playground.service.DatabaseService;
import com.playground.service.FileReaderService;
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
	
	@Test
	public void commitRecords() throws Exception {
		
		DatabaseService databaseService =  new DatabaseService();
		databaseService.commitRecords();		
	}
	
}
