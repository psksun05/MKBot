package com.Matsogeum.kakaoBot;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

public class catchML extends Application {
        @Override
        public void onCreate() {
                super.onCreate();
                SemEmergencyManagerLeakingActivity.applyFix(this);
                if(LeakCanary.isInAnalyzerProcess(this)){
                        return;
                }
                LeakCanary.enableDisplayLeakActivity(this);
                refWatcher = LeakCanary.install(this);
        }

        public static RefWatcher getRefWatcher(Context context){
                catchML catchml = (catchML)context.getApplicationContext();
                return catchml.refWatcher;
        }
        private RefWatcher refWatcher;
}
