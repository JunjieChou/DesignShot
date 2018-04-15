package com.olituc.designshot.domain;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by olituc on 3/13/18.
 * All Rights Reserved by olituc
 */

public class MySpotInfo {

    private int spotId;
    private String spotLocation ="";
    private String spotCity="";
    private String spotRemark="";
    private Bitmap spotPic=null;

    public boolean isPublic() {
        return isPublic;
    }

    public void setPublic(boolean aPublic) {
        isPublic = aPublic;
    }

    boolean isPublic;
    private int userId;

    public int getSpotId() {
        return spotId;
    }

    public void setSpotId(int spotId) {
        this.spotId = spotId;
    }

    public String getSpotLocation() {
        return spotLocation;
    }

    public void setSpotLocation(String spotLocation) {
        this.spotLocation= spotLocation;
    }

    public String getSpotCity() {
        return spotCity;
    }

    public void setSpotCity(String spotCity) {
        this.spotCity = spotCity;
    }

    public String getSpotRemark() {
        return spotRemark;
    }

    public void setSpotRemark(String spotRemark) {
        this.spotRemark = spotRemark;
    }

    public Bitmap getSpotPicBitmap() {
        return spotPic;
    }

    public byte[] getSpotPic(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        this.spotPic.compress(Bitmap.CompressFormat.PNG,50,baos);
        try {
            baos.flush();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return baos.toByteArray();
    }

    public void setSpotPic(Bitmap spotPic) {
        this.spotPic = spotPic;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

}
