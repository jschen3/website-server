package com.jimmy.chen.service;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.MongoClient;

@Path("/mongodbslides")
public class MongoDBSlideService {
	MongoClient mongoClient = new MongoClient("localhost",27017);
	private Morphia morphia = new  Morphia();
	private Datastore datastore = morphia.createDatastore(mongoClient, "website");
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public String showSlides() throws JsonProcessingException{
		List<Slide> slides = datastore.createQuery(Slide.class).asList();
		ObjectMapper mp = new ObjectMapper();
		return mp.writerWithDefaultPrettyPrinter().writeValueAsString(slides);
	}
}
