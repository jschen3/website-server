package utility;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import models.Article;
import models.Project;
import models.Slide;
import models.Teaser;
import models.Teaser;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import constants.WebsiteConstants;


public class FileProcessor {
	static Teaser teaser = new Teaser();
	static WebsiteConstants wc = new WebsiteConstants();
	public static void main(String[] args) throws Exception {
		processSlides();
		processArticles();
		processImages();
		proccessProjects();
		processTeasers();
	}
	private static void processTeasers() throws IOException, ParseException {
		File sourceTeaserFolder = new File(WebsiteConstants.SOURCE_PROBLEM);
		File sourceSolutionFolder = new File(WebsiteConstants.SOURCE_SOLUTION);
		File [] sourceTeasers = sourceTeaserFolder.listFiles();
		File [] sourceSolutions = sourceSolutionFolder.listFiles();
		for(File teaserFile:sourceTeasers){
			if (FilenameUtils.getExtension(teaserFile.getName()).equals("txt")){
				Teaser t = new Teaser();
				t.parseProblemFile(teaserFile);
				for(File solutionFile:sourceSolutions){
					if (solutionFile.getName().equals(t.getLocator()+".txt")){
						t.processFiles(teaserFile, solutionFile);
						System.out.println(teaser);
					}
				}
			}
		}
	}
	public static void processTeasers2() throws IOException, ParseException{
		File sourceTeasers = new File(WebsiteConstants.SOURCE_TEASER);
		File[] sourceTeaserFiles = sourceTeasers.listFiles();
		for(File f:sourceTeaserFiles){
			if (!f.isDirectory()){
				String ext =  FilenameUtils.getExtension(f.getName());
				Teaser t2 = new Teaser();
				if (ext.equals("txt")){
					t2.processFile(f);
				}
			}
		}
	}
	public static void proccessProjects() throws IOException, ParseException {
		File sourceProjectFolder = new File(WebsiteConstants.SOURCE_PROJECTS);
		File[] sourceProjectFiles = sourceProjectFolder.listFiles();
		for (File f:sourceProjectFiles){
			Project p =new Project();
			String ext =  FilenameUtils.getExtension(f.getName());
			if (ext.equals("txt")){
				p.processFile(f);
			}
		}
	}
	public static void processImages() throws IOException {
		InsertFilesIntoDB insert = new InsertFilesIntoDB();
		File sourceImageFolder = new File(WebsiteConstants.SOURCE_IMAGES);
		File archiveImageFolder = new File(WebsiteConstants.IMAGE_ARCHIVE);
		File[] sourceImageFiles = sourceImageFolder.listFiles();
		for(File f:sourceImageFiles){
			if (f.isDirectory()){
				File newDir = new File(WebsiteConstants.IMAGE_ARCHIVE+File.separator+f.getName());
				insert.insertImages(f.getAbsolutePath(), WebsiteConstants.LOCAL_MONGODB, newDir);
				insert.insertImages(f.getAbsolutePath(), WebsiteConstants.REMOTE_MONGODB, newDir);
				f.delete();
			}
		}
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
