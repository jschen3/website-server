package com.jimmy.chen.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import org.apache.commons.io.FilenameUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class FileProcessor {
	static WebsiteConstants wc = new WebsiteConstants();
	public static void main(String[] args) throws Exception {
		//removeExtraFiles();
		processSlides();
		processArticles();
		
	}
	public static void processSlides() throws IOException{
		File sourceSlideFolder=new File(WebsiteConstants.SOURCE_SLIDES);
		File[] files=sourceSlideFolder.listFiles();
		File currentSlideFolder = new File(WebsiteConstants.CURRENT_SLIDES);
		for(File slideFile:files){
			Slide slide = new Slide();
			String ext =  FilenameUtils.getExtension(slideFile.getName());
			if (ext.equals("txt")){
				slide.processFile(slideFile);
				String fileName=slide.getTitle().replace(" ","_").toLowerCase()+".json";
				String filePresentLocation=currentSlideFolder.getAbsolutePath()+File.separator+"present";
				String fileArchiveLocation=currentSlideFolder.getAbsolutePath()+File.separator+"archive";
				File jsonPresentFile = new File(filePresentLocation+File.separator+fileName);
				File jsonArchiveFile = new File(fileArchiveLocation+File.separator+fileName);
				slide.serializeIntoFile(jsonPresentFile);
				slide.serializeIntoFile(jsonArchiveFile);
			//slideFile.delete();
			}
		}
	}
	public static void processArticles() throws Exception{
		File sourceArticleFolder = new File(WebsiteConstants.SOURCE_ARTICLES);
		File[] files=sourceArticleFolder.listFiles();
		File currentArticleFolder = new File(WebsiteConstants.CURRENT_ARTICLES);
		Calendar mCalendar = Calendar.getInstance();  
		String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()).toLowerCase();
		int i=0;
		for(File articleFile:files){
			String ext =  FilenameUtils.getExtension(articleFile.getName());
			if (ext.equals("txt")){
				Article article = new Article();
				article.processFile(articleFile);
				String fileName = article.getTitle().replace(" ", "_").toLowerCase()+".json";
				String filePresentLocation=currentArticleFolder.getAbsoluteFile()+File.separator+"present";
				String fileArchiveLocation=currentArticleFolder.getAbsolutePath()+File.separator+"archive"+File.separator+month;
				String fileAllLocation=currentArticleFolder.getAbsolutePath()+File.separator+"all";
				File jsonPresentFile = new File(filePresentLocation+File.separator+fileName);
				File jsonArchiveFile = new File(fileArchiveLocation+File.separator+fileName);
				File jsonAllFile = new File(fileAllLocation+File.separator+fileName);
				String archiveLocationFolderString = currentArticleFolder.getAbsolutePath()+File.separator+"archive"+File.separator+month;
				File archiveLocationFolder = new File(archiveLocationFolderString);
				if (!archiveLocationFolder.exists())
					archiveLocationFolder.mkdirs();
				article.serializeIntoFile(jsonPresentFile);
				article.serializeIntoFile(jsonArchiveFile);
				article.serializeIntoFile(jsonAllFile);
				System.out.println(i++);
			}

		}
	}

}
