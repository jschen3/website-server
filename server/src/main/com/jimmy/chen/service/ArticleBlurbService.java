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

@Path("/articleblurbs")
public class ArticleBlurbService {
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String showArticleBlurbs() throws JsonParseException, JsonMappingException, IOException{
		File blurbFolder = new File(WebsiteConstants.CURRENT_ARTICLE_BLURBS+File.separator+"present");
		//System.out.println(slideFolder.getAbsolutePath());
		File[] blurbFiles = blurbFolder.listFiles();
		ArrayList<ArticleBlurb> blurbs = new ArrayList<ArticleBlurb>();
		//System.out.println(Arrays.toString(slideFiles));
		ObjectMapper mp = new ObjectMapper();
		for (File blurbFile: blurbFiles){
			ArticleBlurb ab=mp.readValue(blurbFile, ArticleBlurb.class);
			blurbs.add(ab);
		}
		return mp.writerWithDefaultPrettyPrinter().writeValueAsString(blurbs);
	}
}
