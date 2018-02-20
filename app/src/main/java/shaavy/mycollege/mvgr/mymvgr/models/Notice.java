package shaavy.mycollege.mvgr.mymvgr.models;

/**
 * Created by Yash1th on 2/14/2018.
 */

public class Notice {
    private String from;
    private String message;

    public Notice(String from, String message, String image, Long time) {
        this.from = from;
        this.message = message;
        this.image = image;
        this.time = time;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    private String image;
    private Long time;

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
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

    public Notice() {

    }
}
