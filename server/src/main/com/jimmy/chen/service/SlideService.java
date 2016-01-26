package com.jimmy.chen.service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Path("/slides")
public class SlideService {

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String showSlides() throws JsonParseException, JsonMappingException, IOException{
		File slideFolder = new File(WebsiteConstants.CURRENT_SLIDES+File.separator+"present");
		//System.out.println(slideFolder.getAbsolutePath());
		File[] slideFiles = slideFolder.listFiles();
		ArrayList<Slide> slides = new ArrayList<Slide>();
		//System.out.println(Arrays.toString(slideFiles));
		ObjectMapper mp = new ObjectMapper();
		for (File slideFile: slideFiles){
			Slide s=mp.readValue(slideFile, Slide.class);
			slides.add(s);
		}
		return mp.writerWithDefaultPrettyPrinter().writeValueAsString(slides);
	}
}
