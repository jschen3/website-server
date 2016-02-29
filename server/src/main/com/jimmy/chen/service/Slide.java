package com.jimmy.chen.service;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.commons.io.FileUtils;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
@JsonIgnoreProperties({"mongoDB","mongoClient","sCollection"})
public class Slide {
	private String title;
	private String imagePath;
	private String text;
	private String id;
	private boolean readMoreSlide;
	private String readMoreUrl;
	private ArrayList<Link> links;
	private MongoClient mongoClient = new MongoClient();
	private DB mongoDB = mongoClient.getDB("website");
	private DBCollection sCollection=mongoDB.getCollection("slides");
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
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public boolean isReadMoreSlide() {
		return readMoreSlide;
	}
	public void setReadMoreSlide(boolean readMoreSlide) {
		this.readMoreSlide = readMoreSlide;
	}
	public String getReadMoreUrl() {
		return readMoreUrl;
	}
	public void setReadMoreUrl(String readMoreUrl) {
		this.readMoreUrl = readMoreUrl;
	}
	public void processFile(File file) throws IOException{
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
		if (links.size()==1 && links.get(0).getText().equals("Read More")){
			readMoreSlide=true;
			readMoreUrl=links.get(0).getUrl();
			links.remove(0);
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
	public void insertIntoDb(File jsonFile) throws IOException{
		String jsonFileString=FileUtils.readFileToString(jsonFile);
		DBObject articleObj = (DBObject) JSON.parse(jsonFileString);
		sCollection.insert(articleObj);
	}
	
}
