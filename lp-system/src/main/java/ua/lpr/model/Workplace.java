package ua.lpr.model;


/**
 * Simple JavaBean object that represents role of {@link Workplace}.
 *
 * @author Zakhariya Alexander
 * @version 1.0
 */

public class Workplace {

    private int id;

    private String partition;

    private String post;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartition() { return partition; }

    public void setPartition(String partition) { this.partition = partition; }

    public String getPost() { return post; }

    public void setPost(String post) { this.post = post; }


}
