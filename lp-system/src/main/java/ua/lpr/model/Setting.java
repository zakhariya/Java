package ua.lpr.model;

public class Setting {

    private long id;

    private String object;

    private String setting;

    private String value;


    public long getId() { return id; }

    public void setId(long id) { this.id = id; }

    public String getObject() { return object; }

    public void setObject(String object) { this.object = object; }

    public String getSetting() { return setting; }

    public void setSetting(String setting) { this.setting = setting; }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }
}
