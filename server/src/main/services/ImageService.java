package services;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.OPTIONS;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import org.apache.commons.io.FileUtils;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;
import com.mongodb.gridfs.GridFSInputFile;

import constants.WebsiteConstants;

@Path("/images")
public class ImageService {
	MongoClient mongoClient = new MongoClient(WebsiteConstants.LOCAL_MONGODB, 27017);
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
	
//	public Response uploadFile(@PathParam("title") String title,@FormParam("file") InputStream uploadInputStream,@FormParam("file") FormDataContentDisposition fileDetail) throws IOException{
//		String filename=fileDetail.getFileName();
//		GridFS imageStore = new GridFS(mongoDB, title);
//		File f = new File(filename);
//		FileUtils.copyInputStreamToFile(uploadInputStream, f);
//		GridFSInputFile inputFile = imageStore.createFile(f);
//		inputFile.setId(new ObjectId());
//		inputFile.setFilename(f.getName());
//		System.out.println(f.getName());
//		inputFile.save();
//		return Response.ok().build();
//	}
	
}
