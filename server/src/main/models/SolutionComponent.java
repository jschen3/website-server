package models;
import org.mongodb.morphia.annotations.Embedded;
@Embedded
public class SolutionComponent implements Comparable<SolutionComponent>{
	int type;
	int position;
	String content;
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public SolutionComponent(){
	}
	public SolutionComponent(int type, int position, String content){
		this.type=type;
		this.position=position;
		this.content=content;
	}
	@Override
	public String toString() {
		return "SolutionComponent [type=" + type + ", position=" + position + ", content=" + content + "]";
	}
	@Override
	public int compareTo(SolutionComponent o) {
		int compareQuantity=o.getPosition();
		return this.getPosition()-compareQuantity;
	}
}
