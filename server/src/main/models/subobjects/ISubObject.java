package models.subobjects;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;

public class ISubObject implements Comparable<ISubObject>{
	@Id
	private ObjectId id;
	private PositionalObject position;
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public PositionalObject getPosition() {
		return position;
	}
	public void setPosition(PositionalObject position) {
		this.position = position;
	}
	@Override
	public int compareTo(ISubObject o) {
		return o.getPosition().compareTo(getPosition());
	}
	
}
