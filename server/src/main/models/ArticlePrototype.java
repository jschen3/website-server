package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import models.subobjects.Component;
import models.subobjects.ComponentPrototype;

public class ArticlePrototype {
	String title;
	String blurb;
	List<ComponentPrototype> components;
	public ArticlePrototype(){
		
	}
	public ArticlePrototype(String title, String blurb,  List<ComponentPrototype>components ){
		
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBlurb() {
		return blurb;
	}
	public void setBlurb(String blurb) {
		this.blurb = blurb;
	}
	public List<ComponentPrototype> getComponents() {
		return components;
	}
	public void setComponents(List<ComponentPrototype> components) {
		this.components = components;
	}
	public Article convertToArticle() throws ParseException{
		Article article = new Article();
		article.setTitle(this.title);
		
		Date date = new Date();
		SimpleDateFormat dateFormat= new SimpleDateFormat("MMMMM dd, yyyy");
		String dateText=dateFormat.format(date);
		article.setDateText(dateText);
		
		SimpleDateFormat myFormat = new SimpleDateFormat("MM dd yyyy");
		Date jan1 = myFormat.parse("01 01 2016");
		long diff = date.getTime() - jan1.getTime();
		int dateDay=(int) (long) TimeUnit.DAYS.convert(diff,
				TimeUnit.MILLISECONDS);
		article.setDateDay(dateDay);
		
		String _id=UUID.randomUUID().toString().substring(0,10);
		String url = "#/articles/" + _id;
		article.setId(_id);
		article.setUrl(url);
		List<Component> cpList = new ArrayList<Component>();
		for (ComponentPrototype cp:components){
			if (cp.getCode().length()>0){
				Component newCp = new Component(article.getTitle(), "code", cp.getIndex(), cp.getCode());
				cpList.add(newCp);
			}
			if (cp.getImagePath().length()>0){
				Component newCp = new Component(article.getTitle(), "image", cp.getIndex()+1, cp.getImagePath());
				cpList.add(newCp);
			}
			if (cp.getText().length()>0){
				Component newCp = new Component(article.getTitle(), "text", cp.getIndex()+2, cp.getText());
				cpList.add(newCp);
			}
		}
		return article;
	}
}
