package com.jimmy.chen.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.FilenameUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/articles")
public class ArticleService {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String showArticles() throws JsonParseException, JsonMappingException, IOException{
		File articleFolder = new File(WebsiteConstants.CURRENT_ARTICLES+File.separator+"present");
		//System.out.println(slideFolder.getAbsolutePath());
		File[] articleFiles = articleFolder.listFiles();
		ArrayList<Article> articles = new ArrayList<Article>();
		//System.out.println(Arrays.toString(slideFiles));
		ObjectMapper mp = new ObjectMapper();
		for (File articleFile: articleFiles){
			String ext =  FilenameUtils.getExtension(articleFile.getName());
			if (ext.equals("json")){
				Article a=mp.readValue(articleFile, Article.class);
				articles.add(a);
			}
		}
		Collections.sort(articles);
		return mp.writerWithDefaultPrettyPrinter().writeValueAsString(articles);
	}
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String showSpecificArticle(@PathParam("id") String urlId) throws JsonParseException, JsonMappingException, IOException{
		File articleFolder = new File(WebsiteConstants.CURRENT_ARTICLES+File.separator+"all");
		//System.out.println(slideFolder.getAbsolutePath());
		File[] articleFiles = articleFolder.listFiles();
		//System.out.println(Arrays.toString(slideFiles));
		ObjectMapper mp = new ObjectMapper();
		for (File articleFile: articleFiles){
			String ext =  FilenameUtils.getExtension(articleFile.getName());
			if (ext.equals("json")){
				Article a = mp.readValue(articleFile, Article.class);
				if (a.getId().equals(urlId)){
					return mp.writerWithDefaultPrettyPrinter().writeValueAsString(a);
					
				}
			}
		}
		return "article not found";
	}
}
