package models;
import org.mongodb.morphia.annotations.Embedded;
import java.util.ArrayList;
@Embedded
public class SolutionComponent {
	String type;
	int position;
	String content;
	public String getType() {
		return type;
	}
	public void setType(String type) {
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
	public SolutionComponent(String type, int position, String content){
		this.type=type;
		this.position=position;
		this.content=content;
	}
	@Override
	public String toString() {
		return "SolutionComponent [type=" + type + ", position=" + position + ", content=" + content + "]";
	}
}
