package models.subobjects;

import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class PositionalObject {
	private int position;
	private String positionWords;
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getPositionWords() {
		return positionWords;
	}
	public void setPositionWords(String positionWords) {
		this.positionWords = positionWords;
	}
}
