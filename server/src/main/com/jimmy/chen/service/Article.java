package com.jimmy.chen.service;

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
import java.util.UUID;
import java.util.concurrent.TimeUnit;

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

@JsonIgnoreProperties({ "morphia", "mongoClient", "datastore" })
@Entity("articles")
public class Article implements Comparable<Article> {
	private String title;
	private String dateNumber;
	private String dateText;
	private String dateMonth; // change to enum maybe
	private String blurbText;
	private String locator;
	@Id
	private String _id;
	@Reference
	private String url;
	private int dateDay;
	@Embedded
	private ArrayList<ArticleComponent> articleComponents;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDateNumber() {
		return dateNumber;
	}

	public void setDateNumber(String dateNumber) {
		this.dateNumber = dateNumber;
	}

	public String getDateText() {
		return dateText;
	}

	public void setDateText(String dateText) {
		this.dateText = dateText;
	}

	public String getDateMonth() {
		return dateMonth;
	}

	public void setDateMonth(String dateMonth) {
		this.dateMonth = dateMonth;
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

	public ArrayList<ArticleComponent> getArticleComponents() {
		return articleComponents;
	}

	public void setArticleComponents(
			ArrayList<ArticleComponent> articleComponents) {
		this.articleComponents = articleComponents;
	}

	public void setBlurbText(String blurbText) {
		this.blurbText = blurbText;
	}

	public void processFile(File file) throws IOException, ParseException {
		Files.copy(file.toPath(), new File(WebsiteConstants.ARTICLE_ARCHIVE
				+ File.separator + file.getName()).toPath(),
				StandardCopyOption.REPLACE_EXISTING);
		BufferedReader br = new BufferedReader(new FileReader(file));
		this.title = br.readLine();
		this.locator=title.toLowerCase().replace(" ", "_");
		System.out.println(locator);
		this.dateNumber = br.readLine();
		this.dateText = br.readLine();
		this.dateMonth = br.readLine();
		this.blurbText = br.readLine();
		SimpleDateFormat myFormat = new SimpleDateFormat("MM dd yyyy");
		Date date = myFormat.parse(dateNumber.replace("-", " "));
		Date jan1 = myFormat.parse("01 01 2016");
		long diff = date.getTime() - jan1.getTime();
		System.out.println(diff);
		System.out.println(TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS));
		this.dateDay = (int) (long) TimeUnit.DAYS.convert(diff,
				TimeUnit.MILLISECONDS);
		System.out.println(this.dateDay);
		this._id = UUID.randomUUID().toString();
		this.url = "article.html#/?id=" + this._id;
		String next;
		this.articleComponents = new ArrayList<ArticleComponent>();
		while ((next = br.readLine()) != null) {
			ArticleComponent ac = new ArticleComponent();
			ArrayList<String> acImages = new ArrayList<String>();
			ArrayList<String> acText = new ArrayList<String>();
			while (next != null && !next.equals("###")) {
				if (next.substring(0, 6).equals("image:")) {
					String imagePath = next.substring(6);
					acImages.add(imagePath);
				} else {
					acText.add(next);
				}
				next = br.readLine();
			}
			ac.setImages(acImages);
			ac.setTexts(acText);
			articleComponents.add(ac);
		}
		insertIntoDbLocal();
		insertIntoDbRemote();
		file.delete();
	}

	public void serializeIntoFile(File serializeFile) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(serializeFile, this);

	}

	@Override
	public String toString() {
		return "Article [title=" + title + ", dateNumber=" + dateNumber
				+ ", dateText=" + dateText + ", dateMonth=" + dateMonth
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
