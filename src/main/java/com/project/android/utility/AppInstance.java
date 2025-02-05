package com.project.android.utility;

import android.app.Application;
import android.content.Context;

import com.project.android.model.CloakRoomOwner;
import com.project.android.model.Tourist;


public class AppInstance extends Application {
    public static AppInstance instance = null;
    private boolean isAdminUser;
    private CloakRoomOwner currentCloakRoomOwner;
    private Tourist currentTourist;

    private static Context mContext;

    public static Context getInstance()
    {
        if (null == instance) {
            instance = new AppInstance();
        }
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }


    public boolean isAdminUser()
    {
        return isAdminUser;
    }

    public void setAdminUser(boolean isAdminUser)
    {
        this.isAdminUser = isAdminUser;
    }

    public CloakRoomOwner getCurrentCloakRoomOwner() {
        return currentCloakRoomOwner;
    }

    public void setCurrentCloakRoomOwner(CloakRoomOwner currentCloakRoomOwner) {
        this.currentCloakRoomOwner = currentCloakRoomOwner;
    }

    public Tourist getCurrentTourist() {
        return currentTourist;
    }

    public void setCurrentTourist(Tourist currentTourist) {
        this.currentTourist = currentTourist;
    }
}


