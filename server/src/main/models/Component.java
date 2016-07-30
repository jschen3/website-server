package models;
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
	private int position;
	private String content;
	public String getParent() {
		return parent;
	}
	public Component(String parent,  String type, int position, String content){
		
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
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
	public Component(){
	}
	public Component(String type, int position, String content){
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
	public static List<Component> processComponents(Scanner sc) throws IOException{
		return processComponents(sc, null);
	}
	public static List<Component> processComponents(Scanner sc, String stopString) throws IOException{
		List<Component> componentList = new ArrayList<Component>();
		String firstLine;
		while(sc.hasNext()){
			firstLine=sc.nextLine();
			if(firstLine.equals(stopString))
				break;
			else
				componentList.add(getComponent(sc));
		}
		return componentList;
		
	}
	public static Component getComponent(Scanner sc) throws NumberFormatException, IOException{
		String sType=sc.nextLine();
		int sPosition=Integer.parseInt(sc.nextLine());
		String sContent=sc.nextLine();
		return new Component(sType,sPosition,sContent);
	}
}
