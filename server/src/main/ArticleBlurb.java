import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
@JsonIgnoreProperties({"filePath", "dateDay"})
public class ArticleBlurb implements Comparable<ArticleBlurb>{
	private String title;
	private String dateNumber;
	private String dateText;
	private String dateMonth; //change to enum maybe
	private String text;
	private String url;
	private int dateDay;
	private String filePath;
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
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public int getDateDay() {
		return dateDay;
	}
	public void setDateDay(int dateDay) {
		this.dateDay = dateDay;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public void processFile(File file) throws IOException, ParseException{
		this.filePath=file.getAbsolutePath();
		BufferedReader br= new BufferedReader(new FileReader(file));
		this.title=br.readLine();
		this.dateNumber=br.readLine();
		this.dateText=br.readLine();
		this.dateMonth=br.readLine();
		this.text=br.readLine();
		this.url=br.readLine();
		SimpleDateFormat myFormat = new SimpleDateFormat("MM dd yyyy");
		Date date = myFormat.parse(dateNumber.replace("-", " "));
		Date jan1 = myFormat.parse("01 01 2016");
		long diff = date.getTime()-jan1.getTime();
		this.dateDay=(int) TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	}
	public void serializeIntoFile(File serializeFile) throws IOException{
		ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue(serializeFile, this);
		
	}
	@Override
	public String toString() {
		return "ArticleBlurb [title=" + title + ", dateNumber=" + dateNumber
				+ ", dateText=" + dateText + ", dateMonth=" + dateMonth
				+ ", text=" + text + ", url=" + url + ", filePath=" + filePath
				+ "]";
	}
	public String serialize() throws JsonProcessingException{
		ObjectMapper mapper= new ObjectMapper();
		return mapper.writeValueAsString(this);
	}
	@Override
	public int compareTo(ArticleBlurb o) {
		// TODO Auto-generated method stub
		int compareQuantity = o.getDateDay();
		return this.dateDay - compareQuantity;
	}

	
}
