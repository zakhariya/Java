package ua.od.zakhariya.patterns.factory_method.entity;

public class SomeEntity {
	
	private long id;
	
	private String someInfo;
	
	private SomeSubEntity subEntity;
	
	

	public SomeEntity() {
		this.id = 2;
		this.someInfo = "I`m some entity";
		this.subEntity = new SomeSubEntity(this.getClass().getName());
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getSomeInfo() {
		return someInfo;
	}

	public void setSomeInfo(String someInfo) {
		this.someInfo = someInfo;
	}

	public SomeSubEntity getSubEntity() {
		return subEntity;
	}

	public void setSubEntity(SomeSubEntity subEntity) {
		this.subEntity = subEntity;
	}

	@Override
	public String toString() {
		return "SomeEntity [id=" + id + ", someInfo=" + someInfo + ", subEntity=" + subEntity + "]";
	}
}
