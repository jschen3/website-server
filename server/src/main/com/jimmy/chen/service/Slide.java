package com.jimmy.chen.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@JsonIgnoreProperties({"filePath"})
public class Slide {
	private String title;
	private String imagePath;
	private String text;
	private ArrayList<Link> links;
	
	private String filePath;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public ArrayList<Link> getLinks() {
		return links;
	}
	public void setLinks(ArrayList<Link> links) {
		this.links = links;
	}
	public void processFile(File file) throws IOException{
		this.filePath=file.getAbsolutePath();
		BufferedReader br= new BufferedReader(new FileReader(file));
		this.title=br.readLine();
		this.imagePath=br.readLine();
		this.text=br.readLine();
		this.links=new ArrayList<Link>();
		int counter=0;
		String url;
		String text;
		while((url=br.readLine())!=null && !url.equals("")){
			Link link = new Link();
			link.setUrl(url);
			text=br.readLine();
			link.setText(text);
			links.add(link);
		}
	}
	public String serialize() throws JsonProcessingException{
		ObjectMapper mapper= new ObjectMapper();
		return mapper.writeValueAsString(this);
	}
	public void serializeIntoFile(File serializeFile) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(serializeFile, this);
		
	}
	@Override
	public String toString() {
		return "Slide [title=" + title + ", imagePath=" + imagePath + ", text="
				+ text + ", links=" + links + "]";
	}
	
	
}
