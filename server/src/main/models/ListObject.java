package models;

import java.util.ArrayList;

import org.mongodb.morphia.annotations.Entity;

import models.subobjects.ISubObject;
import models.subobjects.PositionalObject;

@Entity("listobject")
public class ListObject implements Comparable<ListObject>{
	private String listName;
	private PositionalObject position;
	private ArrayList<ISubObject> Objects;
	public String getListName() {
		return listName;
	}
	public void setListName(String listName) {
		this.listName = listName;
	}
	public PositionalObject getPosition() {
		return position;
	}
	public void setPosition(PositionalObject position) {
		this.position = position;
	}
	public ArrayList<ISubObject> getObjects() {
		return Objects;
	}
	public void setObjects(ArrayList<ISubObject> objects) {
		Objects = objects;
	}
	@Override
	public int compareTo(ListObject o) {
		// TODO Auto-generated method stub
		return 0;
	}
	
	
}
