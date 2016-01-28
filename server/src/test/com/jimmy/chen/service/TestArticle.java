package com.jimmy.chen.service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import org.junit.Test;

public class TestArticle {
	@Test
	public void testProcessFile() throws IOException, ParseException{
		String articlePath = WebsiteConstants.SOURCE_ARTICLES+"/trip_to_china.txt";
		File f= new File(articlePath);
		Article a = new Article();
		a.processFile(f);
		//System.out.println(a);
	}
	@Test
	public void testSerialize() throws IOException, ParseException{
		String articlePath = WebsiteConstants.SOURCE_ARTICLES+"/trip_to_china.txt";
		File f= new File(articlePath);
		Article a = new Article();
		a.processFile(f);
		System.out.println(a.serialize());
	}
}
