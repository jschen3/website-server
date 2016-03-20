package utility;

import java.io.File;
import java.util.List;

import models.Article;
import models.Slide;

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

import constants.WebsiteConstants;

public class FixCoherence {
	private static MongoClient lMongoClient = new MongoClient(WebsiteConstants.LOCAL_MONGODB,27017);
	private static Morphia morphia = new  Morphia();
	private static Datastore lDataStore = morphia.createDatastore(lMongoClient, "website");
	private static MongoClient rMongoClient = new MongoClient(WebsiteConstants.REMOTE_MONGODB,27017);
	private static Datastore rDataStore = morphia.createDatastore(rMongoClient, "website");
	public static void main(String[] args) {
		fixSlideReadMoreUrlLocal();
		fixSlideReadMoreUrlRemote();
	}
	public static void fixSlideReadMoreUrlLocal(){
		List<Article> articles = lDataStore.createQuery(Article.class).asList();
		for (Article a:articles){
			String title = a.getTitle();
			String url = a.getUrl();
			Query<Slide> slideQueries = lDataStore.createQuery(Slide.class);
			Query<Slide> sameTitleSlide=slideQueries.filter("title ==", title);
			UpdateOperations<Slide> updateOperation = lDataStore.createUpdateOperations(Slide.class).set("readMoreUrl", url);
			lDataStore.update(sameTitleSlide, updateOperation);
		}
	}
	public static void fixSlideReadMoreUrlRemote(){
		List<Article> articles = rDataStore.createQuery(Article.class).asList();
		for (Article a:articles){
			String title = a.getTitle();
			String url = a.getUrl();
			Query<Slide> slideQueries = rDataStore.createQuery(Slide.class);
			Query<Slide> sameTitleSlide=slideQueries.filter("title ==", title);
			UpdateOperations<Slide> updateOperation = rDataStore.createUpdateOperations(Slide.class).set("readMoreUrl", url);
			rDataStore.update(sameTitleSlide, updateOperation);
		}
	}

}
