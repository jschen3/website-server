package com.jimmy.chen.service;

import java.util.ArrayList;

public class ArticleComponent {
	private ArrayList<String> images;
	private ArrayList<String> texts;
	public ArrayList<String> getImage() {
		return images;
	}
	public void setImage(ArrayList<String> images) {
		this.images = images;
	}
	public ArrayList<String> getTexts() {
		return texts;
	}
	public void setText(ArrayList<String> texts) {
		this.texts = texts;
	}
	@Override
	public String toString() {
		return "ArticleComponent [images=" + images + ", texts=" + texts + "]";
	}
	
}
