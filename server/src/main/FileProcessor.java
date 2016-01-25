import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Locale;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class FileProcessor {
	static WebsiteConstants wc = new WebsiteConstants();
	public static void main(String[] args) throws Exception {
		removeExtraFiles();
		processSlides();
		processArticleBlurb();
		
	}
	public static void removeExtraFiles() throws Exception{
		File sourceSlideFolder=new File(WebsiteConstants.SOURCE_SLIDES);
		File sourceABlurbFolder = new File(WebsiteConstants.SOURCE_ARTICLE_BLURBS);
		File currentSlideFolder = new File(WebsiteConstants.CURRENT_SLIDES+File.separator+"present");
		File currentABlurbFolder = new File(WebsiteConstants.CURRENT_ARTICLE_BLURBS+File.separator+"present");
		int newSlideCount = sourceSlideFolder.listFiles().length;
		int newABlurbCount = sourceABlurbFolder.listFiles().length;
		int currentSlideCount = currentSlideFolder.listFiles().length;
		int currentABlurbCount = currentABlurbFolder.listFiles().length;
		if (newSlideCount+currentSlideCount>WebsiteConstants.MAX_SLIDES){
			removeSlideFile((newSlideCount+currentSlideCount)-WebsiteConstants.MAX_SLIDES);
		}
		if (newABlurbCount+currentABlurbCount>WebsiteConstants.MAX_BLURBS){
			removeArticleBlurbFiles((newABlurbCount+currentABlurbCount)-WebsiteConstants.MAX_BLURBS);
		}
		
	}
	public static void removeArticleBlurbFiles(int count) throws JsonParseException, JsonMappingException, IOException{
		ArrayList<ArticleBlurb> blurbs = new ArrayList<ArticleBlurb>();
		File currentABlurbFolder = new File(WebsiteConstants.CURRENT_ARTICLE_BLURBS+File.separator+"present");
		File[] blurbFiles = currentABlurbFolder.listFiles();
		ObjectMapper mp = new ObjectMapper();
		for (File f:blurbFiles){
			blurbs.add(mp.readValue(f, ArticleBlurb.class));
			f.delete();
		}
		Collections.sort(blurbs);
		for (int i=0;i<count;i++){
			blurbs.remove(0);
		}
		for (ArticleBlurb b:blurbs){
			String fileName = b.getTitle().replace(" ", "_").toLowerCase()+".json";
			String filePresentLocation=currentABlurbFolder.getAbsoluteFile()+File.separator+"present";
			File resultFile = new File(filePresentLocation+File.separator+fileName);
			mp.writeValue(resultFile, b);
		}
	}
	public static void removeSlideFile(int count){
		File currentSlideFolder = new File(WebsiteConstants.CURRENT_SLIDES+File.separator+"present");
		File[] slideFiles = currentSlideFolder.listFiles();
		ArrayList<Integer> randomInts = randomNumbers(slideFiles.length);
		for (int i=0;i<count;i++){
			slideFiles[randomInts.get(i)].delete();
		}
	}

	public static ArrayList<Integer> randomNumbers(int max) {
		ArrayList<Integer> list = new ArrayList<Integer>();
		for (int i = 1; i < max; i++) {
			list.add(new Integer(i));
		}
		Collections.shuffle(list);
		return list;
	}
	public static void processSlides() throws IOException{
		File sourceSlideFolder=new File(WebsiteConstants.SOURCE_SLIDES);
		File[] files=sourceSlideFolder.listFiles();
		File currentSlideFolder = new File(WebsiteConstants.CURRENT_SLIDES);
		for(File slideFile:files){
			Slide slide = new Slide();
			slide.processFile(slideFile);
			String fileName=slide.getTitle().replace(" ","_").toLowerCase()+".json";
			String filePresentLocation=currentSlideFolder.getAbsolutePath()+File.separator+"present";
			String fileArchiveLocation=currentSlideFolder.getAbsolutePath()+File.separator+"archive";
			File jsonPresentFile = new File(filePresentLocation+File.separator+fileName);
			File jsonArchiveFile = new File(fileArchiveLocation+File.separator+fileName);
			slide.serializeIntoFile(jsonPresentFile);
			slide.serializeIntoFile(jsonArchiveFile);
			//slideFile.delete();
		}
	}
	public static void processArticleBlurb() throws Exception{
		File sourceBlurbFolder = new File(WebsiteConstants.SOURCE_ARTICLE_BLURBS);
		File[] files=sourceBlurbFolder.listFiles();
		File currentBlurbFolder = new File(WebsiteConstants.CURRENT_ARTICLE_BLURBS);
		Calendar mCalendar = Calendar.getInstance();  
		String month = mCalendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()).toLowerCase();
		for(File blurbFile:files){
			ArticleBlurb articleBlurb = new ArticleBlurb();
			articleBlurb.processFile(blurbFile);
			String fileName = articleBlurb.getTitle().replace(" ", "_").toLowerCase()+".json";
			String filePresentLocation=currentBlurbFolder.getAbsoluteFile()+File.separator+"present";
			String fileArchiveLocation=currentBlurbFolder.getAbsolutePath()+File.separator+"archive"+File.separator+month;
			File jsonPresentFile = new File(filePresentLocation+File.separator+fileName);
			File jsonArchiveFile = new File(fileArchiveLocation+File.separator+fileName);
			articleBlurb.serializeIntoFile(jsonPresentFile);
			articleBlurb.serializeIntoFile(jsonArchiveFile);
			//blurbFile.delete();
		}
	}

}
