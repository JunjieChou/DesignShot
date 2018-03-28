package com.olituc.designshot.domain;

/**
 * Created by olituc on 3/13/18.
 * All Rights Reserved by olituc
 */

public class UserInfo {
    private int userId;
    private String userName;
    private String passWord;
    private String Emails;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmails() {
        return Emails;
    }

    public void setEmails(String emails) {
        Emails = emails;
    }
}
