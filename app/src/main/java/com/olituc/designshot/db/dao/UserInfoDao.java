package com.olituc.designshot.db.dao;

import android.content.Context;

import com.olituc.designshot.db.DesignShotSQLiteOpenHelper;
import com.olituc.designshot.domain.UserInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by olituc on 3/27/18.
 * All Rights Reserved by olituc
 */

public class UserInfoDao {
    private final DesignShotSQLiteOpenHelper mDesignShotSQLiteOpenHelper;
    private List<UserInfo> mUserInfos = new ArrayList<>();
    public UserInfoDao(Context context){
        mDesignShotSQLiteOpenHelper = new DesignShotSQLiteOpenHelper(context);
    }

    /**
     *
     */
}
