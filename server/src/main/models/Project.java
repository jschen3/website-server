package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import com.mongodb.MongoClient;

import constants.WebsiteConstants;

@Entity("projects")
public class Project implements Comparable<Project>{
	@Id
	private String _id;
	private String title;
	private String locator;
	private String dateText;
	private int dateDay;
	private String image;
	private boolean hasDemoLink;
	private String demoLink;
	private String github;
	private String technologies;
	private String summary;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String get_id() {
		return _id;
	}
	public void set_id(String _id) {
		this._id = _id;
	}
	public String getSummary() {
		return summary;
	}
	public void setSummary(String summary) {
		this.summary = summary;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public boolean isHasDemoLink() {
		return hasDemoLink;
	}
	public void setHasDemoLink(boolean hasDemoLink) {
		this.hasDemoLink = hasDemoLink;
	}
	public String getDemoLink() {
		return demoLink;
	}
	public void setDemoLink(String demoLink) {
		this.demoLink = demoLink;
	}
	public String getTechnologies() {
		return technologies;
	}
	public void setTechnologies(String technologies) {
		this.technologies = technologies;
	}
	public String getGithub() {
		return github;
	}
	public void setGithub(String github) {
		this.github = github;
	}
	public String getLocator() {
		return locator;
	}
	public void setLocator(String locator) {
		this.locator = locator;
	}
	public int getDateDay() {
		return dateDay;
	}
	public void setDateDay(int dateDay){
		this.dateDay=dateDay;
	}
	public void processFile(File file) throws IOException, ParseException{
		FileUtils.copyFile(file, new File(WebsiteConstants.PROJECT_ARCHIVE + File.separator + file.getName()));
		BufferedReader br = new BufferedReader(new FileReader(file));
		this.title = br.readLine();
		this.locator = title.toLowerCase().replaceAll("\\W", "");
		this.dateText = br.readLine();
		SimpleDateFormat myFormat = new SimpleDateFormat("MM dd yyyy");
		Date date = myFormat.parse(dateText.replace("-", " "));
		Date jan1 = myFormat.parse("01 01 2016");
		long diff = date.getTime() - jan1.getTime();
		System.out.println(diff);
		System.out.println(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
		this.dateDay = (int) (long) TimeUnit.DAYS.convert(diff,
				TimeUnit.MILLISECONDS);
		System.out.println(this.dateDay);
		this.image = br.readLine();
		this.github = br.readLine();
		this.demoLink = br.readLine();
		if (demoLink.equals("")){
			this.hasDemoLink=false;
		}
		else{
			this.hasDemoLink=true;
		}
		this.technologies = br.readLine();
		this.summary = br.readLine();		
		insertIntoDbLocal();
		insertIntoDbRemote();
		file.delete();
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
	@Override
	public int compareTo(Project o) {
		int compareQuantity = o.getDateDay();
		return compareQuantity - this.getDateDay();
	}
	
}
