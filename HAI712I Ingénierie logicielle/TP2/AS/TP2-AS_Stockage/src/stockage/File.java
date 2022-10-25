package stockage;

public class File extends StorageElement {
	
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public void cat() {
		System.out.println(this.getContent());
	}
	
	@Override
	public int size() {
		return this.content.length();
	}

	public File(String nom, Directory parent, String content) {
		super(nom, parent, content.length());
		this.content = content;
	}
	
	public File(String nom, Directory parent) {
		super(nom, parent, 0);
		this.content = "";
	}
	
	
}
