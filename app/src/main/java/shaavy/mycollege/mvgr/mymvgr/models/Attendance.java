package shaavy.mycollege.mvgr.mymvgr.models;

/**
 * Created by Yash1th on 2/18/2018.
 */

public class Attendance {
    private String result;
    private String from;
    private Long time;
    private String type;
    private String data;

    public Attendance() {

    }

    public Attendance(String result, String from, Long time, String type, String data) {
        this.result = result;
        this.from = from;
        this.time = time;
        this.type = type;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
