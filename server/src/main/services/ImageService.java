package services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

@Path("/images")
public class ImageService {
	MongoClient mongoClient = new MongoClient();
	DB mongoDB = mongoClient.getDB("images");
	@GET
	@Produces({"image/png","image/jpg","image/gif"})
	@Path("{title}/{fileName}")
	public Response displayImage(@PathParam("title") String title,
			@PathParam("fileName") String fileName) throws IOException {
		GridFS imageStore = new GridFS(mongoDB, title);
		GridFSDBFile gridFile = imageStore.findOne(fileName);
		InputStream in = gridFile.getInputStream();
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		int data = in.read();
		while (data >= 0) {
			out.write((char) data);
			data = in.read();
		}
		out.flush();
		return Response.ok(out.toByteArray()).header("Content-Disposition", "inline; filename="+fileName).build();
	}
}
