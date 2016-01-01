package com.playground.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.playground.model.Ticker;
import com.playground.utility.StringToPojo;

/**
 * 
 * @author Guruprasad Bhaskar
 * class to read content of files from a given directory
 */
public class FileReaderService {
	
	private String directoryName;
	private List<File> fileNames ;
	private List<Ticker> tickers;
	private static Logger log = Logger.getLogger(FileReaderService.class);
	
	public FileReaderService() {
		directoryName = "";
		fileNames = new ArrayList<File>();
		tickers = new ArrayList<Ticker>();
	}
	
	/*
	 * load all the files from given directory
	 */
	public List<File> loadDataFiles() throws Exception {
		
		log.debug("++ loadDataFiles");		
		if(directoryName == null) {
			throw new Exception("Please provide a valid directoryName from where to pick up files..");
		}		
		File dir = new File(directoryName);
		
		File[] files = dir.listFiles(); // get all files under the directory
		if(files.length < 1) {
			throw new Exception("No files in the input directory!!");
		}
		for(File file : files) {
			log.debug(file);
			fileNames.add(file);
		}
		log.debug("-- loadDataFiles");
		return fileNames;
	}
	
	/*
	 * read the content of files and add to List
	 */
	public List<Ticker> readDataFiles(List<File> files) throws Exception {
		log.debug("++ readDataFiles");
		if(files.size() == 0) {
			throw new Exception("No new files to read from!!");
		}
		
		for(File file: files) {
			boolean firstLine = true;
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = "";
			while((line = reader.readLine()) != null) {
				if(firstLine == true) {
					firstLine = false;
					continue;
				}else {
					Ticker ticker = new StringToPojo().convertToPojo(line);
					tickers.add(ticker);
				}
			} // end of while loop
		} // end of for loop
		log.debug("-- readDataFiles");
		return tickers;
	}

	public String getDirectoryName() {
		return directoryName;
	}

	public void setDirectoryName(String directoryName) {
		this.directoryName = directoryName;
	}

	public List<File> getFileNames() {
		return fileNames;
	}

	public void setFileNames(List<File> fileNames) {
		this.fileNames = fileNames;
	}

	public List<Ticker> getTickers() {
		return tickers;
	}

	public void setTickers(List<Ticker> tickers) {
		this.tickers = tickers;
	}
}
