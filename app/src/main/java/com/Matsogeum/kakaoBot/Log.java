package com.Matsogeum.kakaoBot;

import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSStaticFunction;

public class Log extends ScriptableObject {
    public static LogAdapter adapter = new LogAdapter();

    @JSStaticFunction
    public static void d(String str){
        adapter.addLog(str);
    }

    @Override
    public String getClassName() {
        return "Log";
    }
}
