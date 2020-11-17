package edu.fdu.journalapp.Model;

public class Journal {

    public String grateful1;
    public String grateful2;
    public String grateful3;
    public String affirmations;
    public String timestamp;
    public String userid;

    public Journal(String grateful1, String grateful2, String grateful3, String affirmations, String timestamp, String userid) {
        this.grateful1 = grateful1;
        this.grateful2 = grateful2;
        this.grateful3 = grateful3;
        this.affirmations = affirmations;
        this.timestamp = timestamp;
        this.userid = userid;
    }

    public Journal() {
    }

    public String getGrateful1() {
        return grateful1;
    }

    public void setGrateful1(String grateful1) {
        this.grateful1 = grateful1;
    }

    public String getGrateful2() {
        return grateful2;
    }

    public void setGrateful2(String grateful2) {
        this.grateful2 = grateful2;
    }

    public String getGrateful3() {
        return grateful3;
    }

    public void setGrateful3(String grateful3) {
        this.grateful3 = grateful3;
    }

    public String getAffirmations() {
        return affirmations;
    }

    public void setAffirmations(String affirmations) {
        this.affirmations = affirmations;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
