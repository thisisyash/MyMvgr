package shaavy.mycollege.mvgr.mymvgr.models;

/**
 * Created by Yash1th on 2/19/2018.
 */

public class ViewFile {
    private String uid;
    private String result;

    public ViewFile() {
    }

    public ViewFile(String uid, String result) {

        this.uid = uid;
        this.result = result;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
