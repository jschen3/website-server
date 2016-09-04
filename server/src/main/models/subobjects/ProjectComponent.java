package models.subobjects;

import java.util.ArrayList;
import org.mongodb.morphia.annotations.*;
import org.bson.types.ObjectId;

public class ProjectComponent extends ISubObject{
	String title;
	@Id
	ObjectId Id;
	ArrayList<Link> links;
	ArrayList<Component> components; 
	//description
	//technologies
	//image
}
