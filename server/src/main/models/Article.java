package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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
import com.mongodb.MongoClient;

import constants.WebsiteConstants;
import models.subobjects.Component;

@Entity("articles")
public class Article implements Comparable<Article> {
	private String title;
	private String date;
	private String blurbText;
	@Id
	private String _id;
	@Reference
	private String url;
	private int dateDay;
	private String dateText;
	@Embedded
	private List<Component> articleComponents;
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getBlurbText() {
		return blurbText;
	}
	
	public void setText(String text) {
		this.blurbText = text;
	}

	public String getId() {
		return _id;
	}

	public void setId(String id) {
		this._id = id;
	}

	public int getDateDay() {
		return dateDay;
	}

	public void setDateDay(int dateDay) {
		this.dateDay = dateDay;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public String getDateText(){
		return this.dateText;
	}
	public void setDateText(String dateText){
		this.dateText=dateText;
	}
	public List<Component> getArticleComponents() {
		return articleComponents;
	}

	public void setArticleComponents(
			ArrayList<Component> articleComponents) {
		this.articleComponents = articleComponents;
	}

	public void setBlurbText(String blurbText) {
		this.blurbText = blurbText;
	}

	public void processFile(File file) throws IOException, ParseException {
		FileUtils.copyFile(file, new File(WebsiteConstants.ARTICLE_ARCHIVE + File.separator + file.getName()));
		Scanner br = new Scanner(file);
		this.title = br.nextLine();
		this.date = br.nextLine();
		this.blurbText = br.nextLine();
		System.out.println(title);
		SimpleDateFormat myFormat = new SimpleDateFormat("MM dd yyyy");
		Date fDate = myFormat.parse(date);
		Date jan1 = myFormat.parse("01 01 2016");
		long diff = fDate.getTime() - jan1.getTime();
		this.dateDay = (int) (long) TimeUnit.DAYS.convert(diff,
				TimeUnit.MILLISECONDS);
		SimpleDateFormat properFormat= new SimpleDateFormat("MMMM dd, yyyy");
		this.dateText=properFormat.format(fDate);
		System.out.println(dateText);
		this._id = UUID.randomUUID().toString().substring(0,10);
		this.url = "#/articles/" + this._id;
		this.articleComponents=Component.processComponents(title, br);
		insertIntoDbLocal();
		//insertIntoDbRemote();
	}

	public void serializeIntoFile(File serializeFile) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(serializeFile, this);

	}

	@Override
	public String toString() {
		return "Article [title=" + title 
				+ ", date=" + date 
				+ ", blurbText=" + blurbText + ", id=" + _id + ", url=" + url
				+ ", dateDay=" + dateDay + ", articleComponents="
				+ articleComponents + "]";
	}

	public String serialize() throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(this);
	}

	@Override
	public int compareTo(Article o) {
		int compareQuantity = o.getDateDay();
		return compareQuantity - this.getDateDay();
	}

	public void insertIntoDbRemote() {
		MongoClient mongoClient = new MongoClient(WebsiteConstants.REMOTE_MONGODB, 27017);
		Morphia morphia = new Morphia();
		Datastore datastore = morphia.createDatastore(mongoClient, "website");
		datastore.save(this);
	}

	public void insertIntoDbLocal() {
		MongoClient mongoClient = new MongoClient(WebsiteConstants.LOCAL_MONGODB, 27017);
		Morphia morphia = new Morphia();
		Datastore datastore = morphia.createDatastore(mongoClient, "website");
		datastore.save(this);
	}

}
