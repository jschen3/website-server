package models;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import constants.WebsiteConstants;

@JsonIgnoreProperties({"morphia","mongoClient","datastore"})
@Entity("slides")
public class Slide {
	private String title;
	private String locator;
	private String imagePath;
	private String text;
	@Id
	private ObjectId _id;
	private boolean readMoreSlide;
	@Reference
	private String readMoreUrl;
	@Embedded
	private ArrayList<Link> links;

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
	public ObjectId getId() {
		return _id;
	}
	public void setId(ObjectId id) {
		this._id = id;
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
		Files.copy(file.toPath(), new File(WebsiteConstants.SLIDE_ARCHIVE
				+ File.separator + file.getName()).toPath(),
				StandardCopyOption.REPLACE_EXISTING);
		BufferedReader br= new BufferedReader(new FileReader(file));
		this.title=br.readLine();
		this.locator=WebsiteConstants.IMAGE_APPEND+title.toLowerCase().replaceAll("\\W","");
		this.imagePath=br.readLine();
		this.imagePath=this.locator+"/"+imagePath;
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
		if (links.size()==1 && links.get(0).getText().toLowerCase().equals("read more")){
			readMoreSlide=true;
			readMoreUrl=links.get(0).getUrl();
			links.remove(0);
		}
		insertIntoDbLocal();
		insertIntoDbRemote();
		file.delete();
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
	public void insertIntoDbRemote(){
		MongoClient mongoClient = new MongoClient(WebsiteConstants.REMOTE_MONGODB,27017);
		Morphia morphia = new  Morphia();
		Datastore datastore = morphia.createDatastore(mongoClient, "website");
		datastore.save(this);
	}
	public void insertIntoDbLocal(){
		MongoClient mongoClient = new MongoClient(WebsiteConstants.LOCAL_MONGODB,27017);
		Morphia morphia = new  Morphia();
		Datastore datastore = morphia.createDatastore(mongoClient, "website");
		datastore.save(this);
	}
}
