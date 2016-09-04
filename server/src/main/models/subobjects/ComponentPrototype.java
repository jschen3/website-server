package models.subobjects;
public class ComponentPrototype {
	int index;
	String text;
	String imagePath;
	String code;
	public ComponentPrototype(){
		
	}
	public ComponentPrototype(int index,String text, String file, String code){
		this.index=index;
		this.text=text;
		this.imagePath=file;
		this.code=code;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getImagePath() {
		return imagePath;
	}
	public void setImagePath(String file) {
		this.imagePath = file;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
}
