package com.Matsogeum.kakaoBot;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.widget.Toast;
import org.jsoup.*;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Function;
import org.mozilla.javascript.ImporterTopLevel;
import org.mozilla.javascript.Script;
import org.mozilla.javascript.Scriptable;
import org.mozilla.javascript.ScriptableObject;

import java.io.File;
import java.io.FileReader;


public class KakaoTalkListener extends NotificationListenerService {

    private static Function responder;
    private static ScriptableObject execScope;
    private static android.content.Context execContext;

    static Script initializeScript() {
        Script script_real = null;
        try {
            final File scriptDir = new File(Environment.getExternalStorageDirectory() + File.separator + "Mbot");
            if (!scriptDir.exists()) scriptDir.mkdirs();
            File script = new File(scriptDir, "response.js");
            if (!script.exists()) {


            }
            Context parseContext = org.mozilla.javascript.Context.enter();
            parseContext.setOptimizationLevel(-1);
            try {
                script_real = parseContext.compileReader(new FileReader(script), script.getName(), 0, null);
                System.out.println("컴파일");
            } catch (Exception e) {
                Log.e("컴파일", "?", e);
                return script_real;
            }
            ScriptableObject scope = new ImporterTopLevel(parseContext);
            execScope = scope;
            ScriptableObject.defineClass(scope,Api.class);
            ScriptableObject.defineClass(scope,FileStream.class);
            ScriptableObject.defineClass(scope,com.Matsogeum.kakaoBot.Log.class);
            script_real.exec(parseContext, scope);
            responder = (Function) scope.get("response", scope);
            Context.exit();
        } catch (Exception e) {
            //Log.e("parser", "?", e);
            com.Matsogeum.kakaoBot.Log.d(e.toString());
            return script_real;
        }
        return script_real;
    }

    @Override
    public void onNotificationPosted(StatusBarNotification sbn) {
        super.onNotificationPosted(sbn);
        Toast.makeText(getApplicationContext(),String.valueOf(getEnable()),Toast.LENGTH_LONG);
        if (!getEnable()) return;

        if (sbn.getPackageName().equals("com.kakao.talk")) {
            Notification.WearableExtender wExt = new Notification.WearableExtender(sbn.getNotification());
            for (Notification.Action act : wExt.getActions())
                if (act.getRemoteInputs() != null && act.getRemoteInputs().length > 0)
                    if (act.title.toString().toLowerCase().contains("reply") ||
                            act.title.toString().toLowerCase().contains("Reply") ||
                            act.title.toString().toLowerCase().contains("답장")) {
                        execContext = getApplicationContext();
                        callResponder(sbn.getNotification().extras.getString(Notification.EXTRA_SUMMARY_TEXT), sbn.getNotification().extras.getString(Notification.EXTRA_TITLE), sbn.getNotification().extras.get("android.text"), act,sbn.getNotification().largeIcon);
                    }
        }

    }
    @Override
    public IBinder onBind(Intent intent){
        return super.onBind(intent);
    }

    @Override
    public void onCreate(){
        super.onDestroy();
        Toast.makeText(this, "KakaoTalk bot service started", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNotificationRemoved(StatusBarNotification sbn) {
        super.onNotificationRemoved(sbn);
        return;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this,"kakaotalk bot service stopped",Toast.LENGTH_SHORT);
    }

    private void callResponder(String room, String sender, Object msg, Notification.Action session, Bitmap profileImage) {
        if (responder == null || execScope == null) initializeScript();
        Context parseContext = Context.enter();
        parseContext.setOptimizationLevel(-1);
        boolean isGroupChat;
        String _msg;
        if (room == null) {
            room = sender; //단체톡방은 이름이 제대로 뜨지만,1:1톡방은 NULL이 뜬다. 그래서 방이름을 보내는사람 이름으로 해줌
            isGroupChat = false; //위 주석에도 말했듯이,1:1 톡방은 NULL,아니면 채팅방 이름이 뜬다.
        } else {
            isGroupChat = true;
        }


        try {
            responder.call(parseContext, execScope, execScope, new Object[]{room, msg, sender, isGroupChat, new SessionCacheReplier(session), new ImageDB(profileImage)});
        } catch (Throwable e) {
            Log.e("parser", "?", e);
        }
    }

    public boolean getEnable() {

        SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        return sp.getBoolean("status", false);
    }

    public static class SessionCacheReplier {
        private Notification.Action session = null;

        private SessionCacheReplier(Notification.Action session) {
            super();
            this.session = session;
        }

        @TargetApi(Build.VERSION_CODES.KITKAT_WATCH)
        public void reply(String value) {
            if (session == null) return;
            Intent sendIntent = new Intent();
            Bundle msg = new Bundle();
            for (RemoteInput inputable : session.getRemoteInputs())
                msg.putCharSequence(inputable.getResultKey(), value);
            RemoteInput.addResultsToIntent(session.getRemoteInputs(), sendIntent, msg);

            try {
                session.actionIntent.send(execContext, 0, sendIntent);
            } catch (PendingIntent.CanceledException e) {

            }
        }

    }

}
