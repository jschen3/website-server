package models;

import java.util.ArrayList;

import org.mongodb.morphia.annotations.Embedded;

import models.subobjects.Link;

public class MenuBar {
	@Embedded
	ArrayList<Link> menuItems;
	
}
