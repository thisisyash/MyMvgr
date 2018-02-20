package shaavy.mycollege.mvgr.mymvgr.models;

/**
 * Created by Yash1th on 2/15/2018.
 */

public class Announcement {
    private String from;
    private String message;
    private String image;
    private Long time;
    private String sclass;


    public Announcement(String from, String message, String image, Long time, String sclass) {
        this.from = from;
        this.message = message;
        this.image = image;
        this.time = time;
        this.sclass = sclass;
    }

    public Announcement() {

    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getSclass() {
        return sclass;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
    }
}
