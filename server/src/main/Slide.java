import java.io.File;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIgnore;
public class Slide {
	private String title;
	private String imagePath;
	private String text;
	private ArrayList<Link> links;
	@JsonIgnore
	private String filePath;
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
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public void processFile(File file){
		
	}
	@Override
	public String toString() {
		return "Slide [title=" + title + ", imagePath=" + imagePath + ", text="
				+ text + ", links=" + links + ", filePath=" + filePath + "]";
	}
	
	
}
