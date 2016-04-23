package models;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Reference;

import com.mongodb.MongoClient;

import constants.WebsiteConstants;
@Entity("teasers")
public class Teaser {
	private String title;
	private String locator;
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
	private ArrayList<ArticleComponent> problem;
	@Embedded
	private ArrayList<SolutionComponent> solution;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getLocator() {
		return locator;
	}
	public void setLocator(String locator) {
		this.locator = locator;
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
	public ArrayList<ArticleComponent> getProblem() {
		return problem;
	}
	public void setProblem(ArrayList<ArticleComponent> problem) {
		this.problem = problem;
	}
	public ArrayList<SolutionComponent> getSolution() {
		return solution;
	}
	public void setSolution(ArrayList<SolutionComponent> solution) {
		this.solution = solution;
	}
	public void processFiles(File problemFile, File solutionFile) throws IOException, ParseException{
		FileUtils.copyFile(problemFile, new File(WebsiteConstants.TEASER_ARCHIVE + File.separator +problemFile.getName()));
		FileUtils.copyFile(solutionFile, new File(WebsiteConstants.TEASER_ARCHIVE + File.separator +problemFile.getName()));
		parseProblemFile(problemFile);
		parseSolutionFile(solutionFile);
		insertIntoDbLocal();
		//insertIntoDbRemote();
	}
	public void parseProblemFile(File problemFile) throws IOException, ParseException{
		BufferedReader br = new BufferedReader(new FileReader(problemFile));
		this.title=br.readLine();
		this.locator=title.toLowerCase().replaceAll("\\W", "");
		this.dateText=br.readLine();
		this.date=br.readLine();
		this.summary=br.readLine();
		this.techniques=br.readLine();
		SimpleDateFormat myFormat = new SimpleDateFormat("MM dd yyyy");
		Date teaserDate = myFormat.parse(date);
		Date jan1 = myFormat.parse("01 01 2016");
		long diff=teaserDate.getTime()-jan1.getTime();
		this.dateDay = (int) (long) TimeUnit.DAYS.convert(diff,
				TimeUnit.MILLISECONDS);
		this._id=UUID.randomUUID().toString();
		this.url="teaser.html#/?id=" + this._id;
		this.problem = new ArrayList<ArticleComponent>();
		String next;
		while ((next = br.readLine()) != null) {
			ArticleComponent ac = new ArticleComponent();
			ArrayList<String> acImages = new ArrayList<String>();
			ArrayList<String> acText = new ArrayList<String>();
			while (next != null&& !next.equals("###")) {
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
			problem.add(ac);
		}
		
	}
	public void parseSolutionFile(File solutionFile) throws NumberFormatException, IOException{
		BufferedReader br2 = new BufferedReader(new FileReader(solutionFile));
		String sNext;
		solution= new ArrayList<SolutionComponent>();
		while ((sNext = br2.readLine()) != null) {
			if (sNext.equals("code:")){
				int position=Integer.parseInt(br2.readLine());
				StringBuilder content = new StringBuilder();
				String line;
				while((line=br2.readLine())!=null && !line.equals("###")){
					content.append(line);
					content.append("\n");
				}
				SolutionComponent s = new SolutionComponent("code",position,content.toString());
				solution.add(s);
			}
			else if (sNext.equals("image:")) {
					int position=Integer.parseInt(br2.readLine());
					String content=br2.readLine();
					SolutionComponent s = new SolutionComponent("image",position, content);
					solution.add(s);
			}
			else if (sNext.equals("text:")){
						int position=Integer.parseInt(br2.readLine());
						String content=br2.readLine();
						SolutionComponent s = new SolutionComponent("text",position,content);
						solution.add(s);
			}
		}	
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
		return "Teaser [title=" + title + ", locator=" + locator + ", date=" + date + ", dateDay=" + dateDay
				+ ", dateText=" + dateText + ", _id=" + _id + ", url=" + url + ", summary=" + summary + ", techniques="
				+ techniques + ", problem=" + problem + ", solution=" + solution + "]";
	}
}
