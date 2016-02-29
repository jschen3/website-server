package com.jimmy.chen.service;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

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

		ObjectMapper mapper = new ObjectMapper();
		if (objectName.equals("article")) {
			insertArticles(folderPath);
		} else if (objectName.equals("slide")) {
			insertSlides(folderPath);
		} else if (objectName.equals("image")) {
			insertImages(folderPath);
		}
	}

	private static void insertImages(String folderPath) throws IOException {
		MongoClient mongoClient = new MongoClient();
		File folder = new File(folderPath);
		DB mongoDB = mongoClient.getDB("images");
		GridFS imageStore = new GridFS(mongoDB, folder.getName());
		File[] folderFiles = folder.listFiles();
		int id = 0;
		for (File f : folderFiles) {
			GridFSInputFile inputFile = imageStore.createFile(f);
			inputFile.setId(id);
			id++;
			inputFile.save();
		}
	}

	private static void insertSlides(String folderPath)
			throws JsonParseException, JsonMappingException, IOException {
		File folder = new File(folderPath);
		File[] folderFiles = folder.listFiles();
		ObjectMapper mapper = new ObjectMapper();
		for (File f : folderFiles) {
			String extension = FilenameUtils.getExtension(f.getName());
			if (extension.equals("json")) {
				Slide s = mapper.readValue(f, Slide.class);
				s.insertIntoDb(f);
			}
		}

	}

	private static void insertArticles(String folderPath)
			throws JsonParseException, JsonMappingException, IOException {
		File folder = new File(folderPath);

		File[] folderFiles = folder.listFiles();
		ObjectMapper mapper = new ObjectMapper();
		for (File f : folderFiles) {
			String extension = FilenameUtils.getExtension(f.getName());
			if (extension.equals("json")) {
				Article a = mapper.readValue(f, Article.class);
				a.insertIntoDb(f);
			}
		}
	}

}
