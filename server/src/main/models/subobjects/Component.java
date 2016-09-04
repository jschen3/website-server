package models.subobjects;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.mongodb.morphia.annotations.Embedded;
@Embedded
public class Component implements Comparable<Component>{
	private String parent;
	private String type;
	@Embedded
	private PositionalObject position;
	private String content;
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
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
	public Component(){
	}
	public Component(String parent, String type, int position, String content){
		this.parent=parent;
		this.type=type;
		this.position=position;
		this.content=content;
	}

	@Override
	public String toString() {
		return "Component [parent=" + parent + ", type=" + type + ", position=" + position + ", content=" + content
				+ "]";
	}
	@Override
	public int compareTo(Component o) {
		int compareQuantity=o.getPosition();
		return this.getPosition()-compareQuantity;
	}
	public static List<Component> processComponents(String parent, Scanner sc) throws IOException{
		return processComponents(parent, sc, null);
	}
	public static List<Component> processComponents(String parent, Scanner sc, String stopString) throws IOException{
		List<Component> componentList = new ArrayList<Component>();
		String firstLine;
		while(sc.hasNext()){
			firstLine=sc.nextLine();
			if(firstLine.equals(stopString))
				break;
			else {
				Component c=getComponent(parent,sc);
				componentList.add(c);
			}
		}
		return componentList;
	}
	public static Component getComponent(String parent, Scanner sc) throws NumberFormatException, IOException{
		String sType=sc.nextLine();
		String sline=sc.nextLine();
		int sPosition=Integer.parseInt(sline.trim());
		StringBuilder sb= new StringBuilder();
		String sContent;
		if (sType.equals("code")){
			while(sc.hasNext()){
				String line=sc.nextLine();
				if (line.equals("###"))
					break;
				else
					sb.append(line+"\n");
			}
			sContent=sb.toString();
		}
		else{
			sContent=sc.nextLine();
		}
		return new Component(parent,sType,sPosition,sContent);
	}
}
