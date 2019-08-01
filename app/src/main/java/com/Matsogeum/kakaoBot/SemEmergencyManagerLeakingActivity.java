package com.Matsogeum.kakaoBot;

import android.app.Activity;
import android.app.Application;
import android.os.Build;
import android.os.Bundle;

import java.lang.reflect.Field;

public final class SemEmergencyManagerLeakingActivity implements Application.ActivityLifecycleCallbacks {


    private Application application;

    private SemEmergencyManagerLeakingActivity(Application application){
        this.application=application;
    }

    public static void applyFix(Application application){
        if(Build.MANUFACTURER.equals("samsung")&& Build.VERSION.SDK_INT>=Build.VERSION_CODES.KITKAT&&Build.VERSION.SDK_INT<=Build.VERSION_CODES.N){
            application.registerActivityLifecycleCallbacks(new SemEmergencyManagerLeakingActivity(application));
        }
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        try{
            swapActivityWithApplicationContext();
        } catch(Exception e){

        }
        application.unregisterActivityLifecycleCallbacks(this);
    }
    private void swapActivityWithApplicationContext()
            throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {
            Class<?> semEmergencyManagerClass = Class.forName("com.samsung.android.emergencymode.SemEmergencyManager");
            Field sInstanceField = semEmergencyManagerClass.getDeclaredField("sInstance");
            sInstanceField.setAccessible(true);
            Object sInstance = sInstanceField.get(null);
            Field mContextField = semEmergencyManagerClass.getDeclaredField("mContext");
            mContextField.setAccessible(true);
            mContextField.set(sInstance, application);

    }
    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) { }

    @Override
    public void onActivityStarted(Activity activity) { }

    @Override
    public void onActivityResumed(Activity activity) { }

    @Override
    public void onActivityPaused(Activity activity) { }

    @Override
    public void onActivityStopped(Activity activity) { }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) { }


}
