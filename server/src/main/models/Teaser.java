package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import com.mongodb.MongoClient;

import constants.WebsiteConstants;

public class Teaser {
	private String title;
	private String date;
	private int dateDay;
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
		this.date=br.nextLine();
		this.summary=br.nextLine();
		this.techniques=br.nextLine();
		SimpleDateFormat myFormat = new SimpleDateFormat("MM dd yyyy");
		Date teaserDate = myFormat.parse(date);
		Date jan1 = myFormat.parse("01 01 2016");
		long diff=teaserDate.getTime()-jan1.getTime();
		this.dateDay = (int) (long) TimeUnit.DAYS.convert(diff,
				TimeUnit.MILLISECONDS);
		this._id=UUID.randomUUID().toString().substring(0,10);
		this.url="#/teaser/" + this._id;
		String stopString="Solution:";
		this.problem=Component.processComponents(br,stopString);
		this.solution=Component.processComponents(br);	
	}
	
	
		
		
	
}
