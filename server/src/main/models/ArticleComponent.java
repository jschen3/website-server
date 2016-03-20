package models;

import java.util.ArrayList;

import org.mongodb.morphia.annotations.Embedded;
	@Embedded
	public class ArticleComponent {
		public ArrayList<String> images;
		public ArrayList<String> texts;
		public ArrayList<String> getImages(){
			return images;
		}
		public ArrayList<String> getTexts(){
			return texts;
		}
		public void setImages(ArrayList<String> acImages) {
			images=acImages;
		}
		public void setTexts(ArrayList<String> acText) {
			texts=acText;	
		}
	}