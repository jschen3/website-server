package com.jimmy.chen.service;

import java.util.ArrayList;

import org.bson.Document;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class FixCoherence {
	private static MongoClient mongoClient  = new MongoClient("localhost", 27017);
	private static MongoDatabase mongoDB=mongoClient.getDatabase("website");
	public static void main(String[] args) {
		MongoCollection<Document> articleCollection = mongoDB.getCollection("articles");
		FindIterable<Document>articles = mongoDB.getCollection("articles").find();
		MongoCollection<Document> slideCollection = mongoDB.getCollection("slides");
		FindIterable<Document>slides = mongoDB.getCollection("slides").find();
		for(Document a:articles){
			String id=a.getString("id");
			String title=a.getString("title");
			String url=a.getString("url");
			for (Document s:slides){
				if (s.getString("title").equals(title)){
					slideCollection.updateOne(new Document("title", title), new Document("$set", new Document("id", id)));
					slideCollection.updateOne(new Document("title", title), new Document("$set", new Document("links.url", "test")));
					
				}
			}
			FindIterable<Document> test = mongoDB.getCollection("slides").find(new Document("title", title));
			for (Document t:test){
				System.out.println(t);
			}
			
		}
	}
}	
