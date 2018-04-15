package com.olituc.designshot;

import android.app.Application;

/**
 * Created by olituc on 4/8/18.
 * All Rights Reserved by olituc
 */
public class DesignShotAPP extends Application {

    private static final int NO_USER_HAS_LOGIN = 0;
    private static final int ONE_USER_HAS_LOGIN = 1;

    private int userStatus;
    private String userName;
    private String userEmail;
    private boolean isSync;

    public boolean isSync() {
        return isSync;
    }

    public void setSync(boolean sync) {
        isSync = sync;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(int userStatus) {
        this.userStatus = userStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        setUserStatus(NO_USER_HAS_LOGIN);
        setUserName("local admin");
        setUserEmail("");
        setSync(false);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        retrieve();
    }

    public void retrieve(){
        setUserName("local admin");
        setUserEmail("");
        setUserStatus(NO_USER_HAS_LOGIN);
        setSync(false);
    }
}
