package com.jimmy.chen.service;

import java.io.File;

import org.bson.Document;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class FixCoherence {
	private static MongoClient mongoClient = new MongoClient();
	private static MongoDatabase db=mongoClient.getDatabase("website");
	public static void main(String[] args) {
		fixSlideReadMoreUrl();

	}
	public static void fixSlideReadMoreUrl(){
		FindIterable<Document> articles=db.getCollection("articles").find();
		MongoCollection<Document> slideCollection=db.getCollection("slides");
		FindIterable<Document> slides = db.getCollection("slides").find();
		for (Document a:articles){
			String id=a.getString("id");
			String title=a.getString("title");
			String url=a.getString("url");
			for(Document s:slides){
				if (s.getString("title").equals(title) && s.getBoolean("readMoreSlide")){
					slideCollection.updateOne(new Document("title", title) , new Document("$set", new Document("readMoreUrl", url)));
					slideCollection.updateOne(new Document("title", title), new Document("$set", new Document("id", id)));
				}
			}
		}
	}

}
