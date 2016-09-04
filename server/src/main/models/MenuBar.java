package models;

import java.util.ArrayList;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;

import models.subobjects.Link;

public class MenuBar {
	@Id
	private ObjectId _id;
	private String name;
	@Embedded
	ArrayList<Link> menuItems;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public ArrayList<Link> getMenuItems() {
		return menuItems;
	}
	public void setMenuItems(ArrayList<Link> menuItems) {
		this.menuItems = menuItems;
	}	
}
