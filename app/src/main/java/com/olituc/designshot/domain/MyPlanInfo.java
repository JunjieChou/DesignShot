package com.olituc.designshot.domain;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by olituc on 3/11/18.
 * All Rights Reserved by olituc
 * 在计划类中进行bitmap到输出字节流的转换，在初始化的时候直接设置为bitmap
 */

public class MyPlanInfo {
    private int planId;
    String planTime;
    String planLocation;
    String planCity;
    String planTheme;
    String planTogether;
    String planRemark;
    Bitmap planPic;
    boolean isPublic;
    int userId;

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        this.isPublic = aPublic;
    }

    public MyPlanInfo(){

    }

    public MyPlanInfo(String planTime , String planCity, String planTheme){
        this.planTheme = planTheme;
        this.planTime = planTime;
        this.planCity = planCity;
    }

    public int getPlanId() {
        return planId;
    }

    public void setPlanId(int planId) {
        this.planId = planId;
    }

    public String getPlanTime() {
        return planTime;
    }

    public void setPlanTime(String planTime) {
        this.planTime = planTime;
    }

    public String getPlanLocation() {
        return planLocation;
    }

    public void setPlanLocation(String planLocation) {
        this.planLocation = planLocation;
    }

    public String getPlanCity() {
        return planCity;
    }

    public void setPlanCity(String planCity) {
        this.planCity = planCity;
    }

    public String getPlanTheme() {
        return planTheme;
    }

    public void setPlanTheme(String planTheme) {
        this.planTheme = planTheme;
    }

    public String getPlanTogether() {
        return planTogether;
    }

    public void setPlanTogether(String planTogether) {
        this.planTogether = planTogether;
    }

    public String getPlanRemark() {
        return planRemark;
    }

    public void setPlanRemark(String planRemark) {
        this.planRemark = planRemark;
    }

    public byte[] getPlanPic() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        this.planPic.compress(Bitmap.CompressFormat.PNG,50,out);
        try {
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return out.toByteArray();
    }

    public void setPlanPic(Bitmap planPic) {
        this.planPic = planPic;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
