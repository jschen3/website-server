package models;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import models.Slide;

import org.junit.Test;


public class TestSlide {
	@Test
	public void testProcessFile() throws IOException{
		File f= new File("/Users/Jimmy/Desktop/website/slides/introduction.txt");
		Slide s = new Slide();
		s.processFile(f);
		//System.out.println(s.toString());
	}	
	@Test
	public void testSerialization() throws IOException{
		File f= new File("/Users/Jimmy/Desktop/website/slides/introduction.txt");
		Slide s = new Slide();
		s.processFile(f);
		//System.out.println(s.serialize());
	}
	@Test
	public void testFindCurrentSlides(){
		//File f= new File(WebsiteConstants.CURRENT_SLIDES+File.separator+"present");
		//File[] listF=f.listFiles();
		//System.out.println(Arrays.toString(listF));
	}
}
