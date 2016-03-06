package com.jimmy.chen.service;

import java.io.File;
import java.util.List;

import org.bson.Document;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class FixCoherence {
	private static MongoClient mongoClient = new MongoClient("localhost",27017);
	private static Morphia morphia = new  Morphia();
	private static Datastore dataStore = morphia.createDatastore(mongoClient, "website");
	public static void main(String[] args) {
		fixSlideReadMoreUrl();

	}
	public static void fixSlideReadMoreUrl(){
		List<Article> articles = dataStore.createQuery(Article.class).asList();
		for (Article a:articles){
			String title = a.getTitle();
			String url = a.getUrl();
			Query<Slide> slideQueries = dataStore.createQuery(Slide.class);
			Query<Slide> sameTitleSlide=slideQueries.filter("title ==", title);
			UpdateOperations<Slide> updateOperation = dataStore.createUpdateOperations(Slide.class).set("readMoreUrl", url);
			dataStore.update(sameTitleSlide, updateOperation);
		}
	}

}
