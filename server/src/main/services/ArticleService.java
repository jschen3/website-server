package services;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import models.Article;
import models.ArticlePrototype;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;

import constants.WebsiteConstants;

@Path("/articles")
public class ArticleService {
	MongoClient mongoClient = new MongoClient(WebsiteConstants.REMOTE_MONGODB,27017);
	private Morphia morphia = new  Morphia();
	private Datastore datastore = morphia.createDatastore(mongoClient, "website");
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String showArticles() throws JsonProcessingException{
		List<Article> articles = datastore.createQuery(Article.class).asList();
		ObjectMapper mp = new ObjectMapper();
		Collections.sort(articles);
		return mp.writerWithDefaultPrettyPrinter().writeValueAsString(articles);
	}
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getArticle(@PathParam("id") String id) throws JsonProcessingException{
		Query<Article> query = datastore.createQuery(Article.class);

		List<Article> articles=query.filter("_id ==", id).asList();
		ObjectMapper mp = new ObjectMapper();
		if (articles.size()<1)
			return "article not found";
		else{
			Article a=articles.get(0);
			return mp.writerWithDefaultPrettyPrinter().writeValueAsString(a);
		}
	}
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	public void addArticle(ArticlePrototype articlePrototype){
		
	}
}
