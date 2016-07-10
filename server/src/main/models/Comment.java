package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.mongodb.MongoClient;

import constants.WebsiteConstants;

@Entity("comments")
public class Comment {
	@Id
	private String id;
	private String elementId;
	private String dateString;
	private String date;
	private int dateDay;
	private String author;
	private String text;
	public Comment(){
		
	}
	public Comment(String elementId, String dateString, String author, String text) throws ParseException{
		this.elementId=elementId;
		this.id=UUID.randomUUID().toString();
		SimpleDateFormat myFormat = new SimpleDateFormat("MM dd yyyy");
		Date date = myFormat.parse(dateString);
		Date jan1 = myFormat.parse("01 01 2016");
		long diff = date.getTime() - jan1.getTime();
		this.author=author;
		this.dateDay = (int) (long) TimeUnit.DAYS.convert(diff,
				TimeUnit.MILLISECONDS);
		this.text=text;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getElementId() {
		return elementId;
	}
	public void setElementId(String elementId) {
		this.elementId = elementId;
	}
	public String getDateString() {
		return dateString;
	}
	public void setDateString(String dateString) throws ParseException {
		this.dateString = dateString;
		SimpleDateFormat myFormat = new SimpleDateFormat("MM dd yyyy");
		SimpleDateFormat properForm=new SimpleDateFormat("MMMM dd, yyyy");
		Date date = myFormat.parse(dateString);
		Date jan1 = myFormat.parse("01 01 2016");
		long diff = date.getTime() - jan1.getTime();
		this.dateDay = (int) (long) TimeUnit.DAYS.convert(diff,
				TimeUnit.MILLISECONDS);
		this.date=properForm.format(date);
	}
	public int getDateDay() {
		return dateDay;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setDateDay(int dateDay) {
		this.dateDay = dateDay;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public void insertIntoDbLocal() {
		MongoClient mongoClient = new MongoClient(WebsiteConstants.LOCAL_MONGODB, 27017);
		Morphia morphia = new Morphia();
		Datastore datastore = morphia.createDatastore(mongoClient, "website");
		datastore.save(this);
	}
	public void insertIntoDbRemote() {
		MongoClient mongoClient = new MongoClient(WebsiteConstants.REMOTE_MONGODB, 27017);
		Morphia morphia = new Morphia();
		Datastore datastore = morphia.createDatastore(mongoClient, "website");
		datastore.save(this);
	}
	
}
