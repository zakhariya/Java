package ua.lpr.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.io.IOUtils;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;
import java.io.IOException;
import java.sql.Blob;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Base64;
import java.util.Objects;

public class Task {

    private long id, clientId;
    private int priority;
    private String clientName;
    private String status, title, notes, userName, addedUser;
    private boolean deleted;
    private Timestamp addTime, startTime, endTime, planeTime;
    private Blob imageData;
    private String imageBase64String;


//    private BufferedImage image;
//    private Image img;
//    private byte[] imageBytes;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @JsonIgnore
    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @JsonIgnore
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @JsonIgnore
    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }

    @JsonIgnore
    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    @JsonIgnore
    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    @JsonIgnore
    public Timestamp getEndTime() {
        return endTime;
    }

    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }

    @JsonIgnore
    public Timestamp getPlaneTime() {
        return planeTime;
    }

    public void setPlaneTime(Timestamp planeTime) {
        this.planeTime = planeTime;
    }

    @JsonIgnore
    public long getClientId() { return clientId; }

    public void setClientId(long clientId) { this.clientId = clientId; }

    public String getClientName() { return clientName; }

    public void setClientName(String clientName) { this.clientName = clientName; }

    public String getAddedUser() { return addedUser; }

    public void setAddedUser(String addedUser) { this.addedUser = addedUser; }

    @JsonIgnore // disable this one
    public Blob getImageData() {
        return imageData;
    }

//    // serialize as data uri instead
    @JsonProperty("imageData")
    public String getPhotoBase64() {
        // just assuming it is a jpeg. you would need to cater for different media types
        try{
            return "data:image/jpeg;base64," + new String(Base64.getEncoder().encode(IOUtils.toByteArray(imageData.getBinaryStream())));
        }catch (IOException | SQLException | NullPointerException ex){
            return null;
        }
    }

    public void setImageData(Blob imageData) {
        this.imageData = imageData;
    }

    public String getImageBase64String() {
        return imageBase64String;
    }

    public void setImageBase64String(String imageBase64String) throws SerialException, SQLException {
        if(imageBase64String != null && imageBase64String.length() > 0)
            setImageData(new SerialBlob(Base64.getDecoder().decode(imageBase64String)));

        this.imageBase64String = imageBase64String;
    }
//    public BufferedImage getImage() {

//        return image;
//    }
//
//    public void setImage(BufferedImage image) {
//        this.image = image;
//    }
//
//    public Image getImg() { return img; }
//
//    public void setImg(Image img) { this.img = img; }
//
//    public byte[] getImageBytes(){
//        return imageBytes;
//    }
//
//
//    public Image getImageFromBytes(byte [] bytes) {
//        Image result = null;
//
//        if (this.image != null && this.imageBytes.length > 0)
//            result = new ImageIcon(this.image).getImage();
//
//        return result;
//    }
//
//    public void setImageBytes(byte[] imageBytes){
//        this.imageBytes = imageBytes;
//    }
//
//    public void setImageBytes(Image source) {
//        BufferedImage buffered = new BufferedImage(source.getWidth(null), source.getHeight(null), BufferedImage.TYPE_INT_RGB);
//        Graphics2D g = buffered.createGraphics();
//        g.drawImage(source, 0, 0, null);
//        g.dispose();
//
//        ByteArrayOutputStream stream = new ByteArrayOutputStream();
//        try {
//            ImageIO.write(buffered, "JPEG", stream);
//            this.imageBytes = stream.toByteArray();
//        }
//        catch (IOException e) {
//            assert (false); // should never happen
//        }
//    }
/*  jackson very ругается!

    public InputStream getImageInputStream (){
        try {
            return imageData.getBinaryStream();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
*/
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", clientId=" + clientId +
                ", priority=" + priority +
                ", clientName='" + clientName + '\'' +
                ", status='" + status + '\'' +
                ", title='" + title + '\'' +
                ", notes='" + notes + '\'' +
                ", userName='" + userName + '\'' +
                ", addedUser='" + addedUser + '\'' +
                ", delete=" + deleted +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", planeTime=" + planeTime +
                ", imageData=" + imageData +
//                ", imageBase64String=" + imageBase64String +
//                ", image=" + image +
//                ", img=" + img +
//                ", imageBytes=" + Arrays.toString(imageBytes) +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id &&
                clientId == task.clientId &&
                priority == task.priority &&
                deleted == task.deleted &&
                Objects.equals(clientName, task.clientName) &&
                Objects.equals(status, task.status) &&
                Objects.equals(title, task.title) &&
                Objects.equals(notes, task.notes) &&
                Objects.equals(userName, task.userName) &&
                Objects.equals(addedUser, task.addedUser) &&
                Objects.equals(startTime, task.startTime) &&
                Objects.equals(endTime, task.endTime) &&
                Objects.equals(planeTime, task.planeTime) &&
                Objects.equals(imageData, task.imageData) &&
                Objects.equals(imageBase64String, task.imageBase64String);
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, clientId, priority, clientName, status, title, notes, userName, addedUser, deleted, startTime, endTime, planeTime, imageData, imageBase64String);
    }
}
