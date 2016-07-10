package services;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
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
import models.Comment;

@Path("/comments")
public class CommentService {
	MongoClient mongoClient = new MongoClient(WebsiteConstants.LOCAL_MONGODB, 27017);
	private Morphia morphia = new Morphia();
	private Datastore datastore = morphia.createDatastore(mongoClient, "website");
	private ObjectMapper mp = new ObjectMapper();
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("{id}")
	public String getComments(@PathParam("id") String id) throws JsonProcessingException{
		Query<Comment> query=datastore.createQuery(Comment.class);
		List<Comment> comments=query.filter("elementId ==", id).asList();
		return mp.writerWithDefaultPrettyPrinter().writeValueAsString(comments);
	}
	@POST
	@Path("{id}")
	@Consumes(MediaType.APPLICATION_JSON)
	public void addComment(@PathParam("id") String id, Comment comment){
		comment.insertIntoDbLocal();
		comment.insertIntoDbRemote();
	}
	
}
