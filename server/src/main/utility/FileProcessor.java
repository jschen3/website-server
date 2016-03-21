package utility;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import models.Article;
import models.Slide;

import org.apache.commons.io.FilenameUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import constants.WebsiteConstants;


public class FileProcessor {
	static WebsiteConstants wc = new WebsiteConstants();
	public static void main(String[] args) throws Exception {
		processSlides();
		//processArticles();
		
	}
	public static void processSlides() throws IOException{
		File sourceSlideFolder=new File(WebsiteConstants.SOURCE_SLIDES);
		File[] files=sourceSlideFolder.listFiles();
		for(File slideFile:files){
			Slide slide = new Slide();
			String ext =  FilenameUtils.getExtension(slideFile.getName());
			if (ext.equals("txt")){
				slide.processFile(slideFile);
			}
		}
	}
	public static void processArticles() throws Exception{
		File sourceArticleFolder = new File(WebsiteConstants.SOURCE_ARTICLES);
		File[] files=sourceArticleFolder.listFiles();
		Calendar mCalendar = Calendar.getInstance();  
		String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()).toLowerCase();
		int i=0;
		for(File articleFile:files){
			String ext =  FilenameUtils.getExtension(articleFile.getName());
			if (ext.equals("txt")){
				Article article = new Article();
				article.processFile(articleFile);
			}

		}
	}

}
