import java.io.File;
import java.io.IOException;

import org.junit.Test;


public class TestSlide {
	@Test
	public void testProcessFile() throws IOException{
		File f= new File("/Users/Jimmy/Desktop/website/slides/introduction.txt");
		Slide s = new Slide();
		s.processFile(f);
		System.out.println(s.toString());
	}
}
