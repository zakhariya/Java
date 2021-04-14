package ua.od.zakhariya.patterns.factory_method.entity;

public class AnotherEntity {
	
	private long id;
	
	private String someInfo;
	
	private String moreInfo;
	
	private SomeSubEntity subEntity;
	
	

	public AnotherEntity() {
		this.id = 1;
		this.someInfo = "I`m another entity";
		this.moreInfo = "This is aboum something";
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

	public String getMoreInfo() {
		return moreInfo;
	}

	public void setMoreInfo(String moreInfo) {
		this.moreInfo = moreInfo;
	}

	public SomeSubEntity getSubEntity() {
		return subEntity;
	}

	public void setSubEntity(SomeSubEntity subEntity) {
		this.subEntity = subEntity;
	}

	@Override
	public String toString() {
		return "AnotherEntity [id=" + id + ", someInfo=" + someInfo + ", moreInfo=" + moreInfo + ", subEntity="
				+ subEntity + "]";
	}
}
