package com.jimmy.chen.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/articles")
public class ArticleService {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String showArticleBlurbs() throws JsonParseException, JsonMappingException, IOException{
		File articleFolder = new File(WebsiteConstants.CURRENT_ARTICLES+File.separator+"present");
		//System.out.println(slideFolder.getAbsolutePath());
		File[] articleFiles = articleFolder.listFiles();
		ArrayList<Article> articles = new ArrayList<Article>();
		//System.out.println(Arrays.toString(slideFiles));
		ObjectMapper mp = new ObjectMapper();
		for (File articleFile: articleFiles){
			Article a=mp.readValue(articleFile, Article.class);
			articles.add(a);
		}
		return mp.writerWithDefaultPrettyPrinter().writeValueAsString(articles);
	}
	
}
