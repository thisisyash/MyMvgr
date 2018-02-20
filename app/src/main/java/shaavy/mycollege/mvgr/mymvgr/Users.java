package shaavy.mycollege.mvgr.mymvgr;


public class Users extends UserId {

    String name;
    String image;
    String sclass;
    String cid;
    String type;
    String uid;
    String token_id;
    String mobile;

    public Users(String name, String image, String sclass, String cid, String type, String uid, String token_id, String mobile) {
        this.name = name;
        this.image = image;
        this.sclass = sclass;
        this.cid = cid;
        this.type = type;
        this.uid = uid;
        this.token_id = token_id;
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getToken_id() {
        return token_id;
    }

    public void setToken_id(String token_id) {
        this.token_id = token_id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public Users(){

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSclass() {
        return sclass;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
