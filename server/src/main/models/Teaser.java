package models;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import com.mongodb.MongoClient;

import constants.WebsiteConstants;
import models.subobjects.Component;
@Entity("teasers")
public class Teaser {
	private String title;
	private String date;
	private int dateDay;
	private String dateText;
	@Id
	private String _id;
	@Reference
	private String url;
	private String summary;
	private String techniques;
	@Embedded
	private List<Component> problem;
	@Embedded
	private List<Component> solution;
	public Teaser(){
		
	}
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

	public int getDateDay() {
		return dateDay;
	}

	public void setDateDay(int dateDay) {
		this.dateDay = dateDay;
	}

	public String get_id() {
		return _id;
	}

	public void set_id(String _id) {
		this._id = _id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getTechniques() {
		return techniques;
	}

	public void setTechniques(String techniques) {
		this.techniques = techniques;
	}
	public String getDateText(){
		return this.dateText;
	}
	public void setDateText(String dateText){
		this.dateText=dateText;
	}
	public List<Component> getProblem() {
		return problem;
	}

	public void setProblem(List<Component> problem) {
		this.problem = problem;
	}

	public List<Component> getSolution() {
		return solution;
	}

	public void setSolution(List<Component> solution) {
		this.solution = solution;
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
	public String toString() {
		return "Teaser [title=" + title + ", date=" + date + ", dateDay=" + dateDay + ", _id=" + _id + ", url=" + url
				+ ", summary=" + summary + ", techniques=" + techniques + ", problem=" + problem + ", solution="
				+ solution + "]";
	}
	public void processFile(File problemFile) throws IOException, ParseException{
		Scanner br=new Scanner(problemFile);
		this.title=br.nextLine();
		System.out.println(title);
		this.date=br.nextLine();
		this.summary=br.nextLine();
		this.techniques=br.nextLine();
		SimpleDateFormat myFormat = new SimpleDateFormat("MM dd yyyy");
		Date teaserDate = myFormat.parse(date);
		Date jan1 = myFormat.parse("01 01 2016");
		long diff=teaserDate.getTime()-jan1.getTime();
		SimpleDateFormat properFormat = new SimpleDateFormat("MMMM dd, yyyy");
		this.dateText=properFormat.format(teaserDate);
		this.dateDay = (int) (long) TimeUnit.DAYS.convert(diff,
				TimeUnit.MILLISECONDS);
		this._id=UUID.randomUUID().toString().substring(0,10);
		this.url="#/teaser/" + this._id;
		this.problem=Component.processComponents(title,br);
		String solutionTitle=this.title.replace(" ","").toLowerCase();
		File solutionFile=new File(WebsiteConstants.SOURCE_SOLUTION+File.separator+solutionTitle+"-solution.txt");
		processSolutionFile(solutionFile);
		insertIntoDbLocal();
	}
	public void processSolutionFile(File f) throws IOException{
		Scanner sc = new Scanner(f);
		String parent=sc.nextLine();
		this.solution=Component.processComponents(parent, sc);
	}
}
