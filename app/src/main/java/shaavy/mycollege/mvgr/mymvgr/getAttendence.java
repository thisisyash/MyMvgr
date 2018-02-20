package shaavy.mycollege.mvgr.mymvgr;

public class getAttendence {
    private String reg_no;
    private Double percentage;

    public getAttendence(String reg_no, Double percentage) {
        this.reg_no = reg_no;
        this.percentage = percentage;
    }

    public String getReg_no() {
        return reg_no;
    }

    public void setReg_no(String reg_no) {
        this.reg_no = reg_no;
    }

    public Double getPercentage() {
        return percentage;
    }

    public void setPercentage(Double percentage) {
        this.percentage = percentage;
    }
}
