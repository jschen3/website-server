package services;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.query.Query;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;

import constants.WebsiteConstants;
import models.Article;
import models.Project;
import models.Teaser;
@Path("/teasers")
public class TeaserService {
	MongoClient mongoClient = new MongoClient(WebsiteConstants.LOCAL_MONGODB, 27017);
	private Morphia morphia = new Morphia();
	private Datastore datastore = morphia.createDatastore(mongoClient, "website");
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String showProjects() throws JsonProcessingException{
		List<Teaser> teasers = datastore.createQuery(Teaser.class).asList();
		ObjectMapper mp = new ObjectMapper();
		return mp.writerWithDefaultPrettyPrinter().writeValueAsString(teasers);
	}
	@GET
	@Path("{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getArticle(@PathParam("id") String id) throws JsonProcessingException{
		Query<Teaser> query = datastore.createQuery(Teaser.class);
		List<Teaser> articles=query.filter("_id ==", id).asList();
		ObjectMapper mp = new ObjectMapper();
		if (articles.size()<1)
			return "article not found";
		else{
			Teaser a=articles.get(0);
			return mp.writerWithDefaultPrettyPrinter().writeValueAsString(a);
		}
	}
}
