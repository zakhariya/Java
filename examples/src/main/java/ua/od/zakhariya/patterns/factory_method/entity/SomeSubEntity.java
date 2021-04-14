package ua.od.zakhariya.patterns.factory_method.entity;

public class SomeSubEntity {
	
	private long id;
	
	private String someSubInfo;
	
	

	public SomeSubEntity(String entityName) {
		this.someSubInfo = "This is some sub info aboun " + entityName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSomeSubInfo() {
		return someSubInfo;
	}

	public void setSomeSubInfo(String someSubInfo) {
		this.someSubInfo = someSubInfo;
	}

	@Override
	public String toString() {
		return "SomeSubEntity [id=" + id + ", someSubInfo=" + someSubInfo + "]";
	}
}
