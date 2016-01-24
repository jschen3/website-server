import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class ArticleBlurb {
	private String title;
	private String dateNumber;
	private String dateText;
	private String dateMonth; //change to enum maybe
	private String text;
	private String url;
	@JsonIgnore
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
	public void processFile(File file) throws IOException{
		this.filePath=file.getAbsolutePath();
		BufferedReader br= new BufferedReader(new FileReader(file));
		this.title=br.readLine();
		this.dateNumber=br.readLine();
		this.dateText=br.readLine();
		this.dateMonth=br.readLine();
		this.text=br.readLine();
		this.url=br.readLine();
	}
	@Override
	public String toString() {
		return "ArticleBlurb [title=" + title + ", dateNumber=" + dateNumber
				+ ", dateText=" + dateText + ", dateMonth=" + dateMonth
				+ ", text=" + text + ", url=" + url + ", filePath=" + filePath
				+ "]";
	}
	
}
