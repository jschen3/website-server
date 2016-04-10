package utility;

import java.io.File;
import java.io.IOException;

import models.Article;
import models.Slide;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.bson.types.ObjectId;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

import constants.WebsiteConstants;

public class InsertFilesIntoDB {
	static String folderPath;
	static String objectName;

	public static void main(String[] args) throws JsonParseException,
			JsonMappingException, IOException {
		if (args.length < 2) {
			System.out
					.println("Not enough arguemnts: usage: filetoinsert tablename");
			System.exit(1);
		}
		folderPath = args[0];
		objectName = args[1];
		InsertFilesIntoDB insert = new InsertFilesIntoDB();
		ObjectMapper mapper = new ObjectMapper();
		if (objectName.equals("article")) {
			insert.insertArticles(folderPath);
		} else if (objectName.equals("slide")) {
			insert.insertSlides(folderPath);
		} else if (objectName.equals("image")) {
			//insert.insertImages(folderPath, WebsiteConstants.LOCAL_MONGODB);
			//insert.insertImages(folderPath, WebsiteConstants.REMOTE_MONGODB);
		} else if (objectName.equals("imagefolder")){
			insert.insertImageFolders(folderPath, WebsiteConstants.LOCAL_MONGODB);
			insert.insertImageFolders(folderPath, WebsiteConstants.REMOTE_MONGODB);
		}
	}

	public void insertImages(String folderPath, String mongoAddress, File archiveFolder) throws IOException {
		MongoClient mongoClient = new MongoClient(mongoAddress, 27017);
		File folder = new File(folderPath);
		DB mongoDB = mongoClient.getDB("images");
		GridFS imageStore = new GridFS(mongoDB, folder.getName());
		System.out.println(folder.getName());
		File[] folderFiles = folder.listFiles();
		ObjectId o=new ObjectId();
		for (File f : folderFiles) {
			GridFSInputFile inputFile = imageStore.createFile(f);
			inputFile.setId(new ObjectId());
			inputFile.setFilename(f.getName());
			System.out.println(f.getName());
			inputFile.save();
			FileUtils.copyFileToDirectory(f, archiveFolder, false);
		}
	}
	public void insertImageFolders(String folderPath, String mongoAddress) throws IOException{
		MongoClient mongoClient = new MongoClient(mongoAddress, 27017);
		File folder = new File(folderPath);
		DB mongoDB = mongoClient.getDB("images");
		File[] insideFolders = folder.listFiles();
		for (File imageFolder:insideFolders){
			if (imageFolder.isDirectory()){
				GridFS imageStore = new GridFS(mongoDB, imageFolder.getName());
				File[] imageFolderFiles = imageFolder.listFiles();	
				for (File f:imageFolderFiles){
					GridFSInputFile inputFile = imageStore.createFile(f);
					inputFile.setId(new ObjectId());
					inputFile.setFilename(f.getName());
					System.out.println(f.getName());
					inputFile.save();
				}
			}
		}
	}
	public void insertSlides(String folderPath)
			throws JsonParseException, JsonMappingException, IOException {
		File folder = new File(folderPath);
		File[] folderFiles = folder.listFiles();
		ObjectMapper mapper = new ObjectMapper();
		for (File f : folderFiles) {
			String extension = FilenameUtils.getExtension(f.getName());
			if (extension.equals("json")) {
				Slide s = mapper.readValue(f, Slide.class);
				s.insertIntoDbLocal();
				s.insertIntoDbRemote();
			}
		}

	}

	public void insertArticles(String folderPath)
			throws JsonParseException, JsonMappingException, IOException {
		File folder = new File(folderPath);

		File[] folderFiles = folder.listFiles();
		ObjectMapper mapper = new ObjectMapper();
		for (File f : folderFiles) {
			String extension = FilenameUtils.getExtension(f.getName());
			if (extension.equals("json")) {
				Article a = mapper.readValue(f, Article.class);
				a.insertIntoDbLocal();
				a.insertIntoDbRemote();
			}
		}
	}

}
