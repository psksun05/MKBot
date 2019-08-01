package com.Matsogeum.kakaoBot;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LogItem {
    private String timeStr;
    private String logStr;

    public void setTimeStr(String time){
        timeStr = time;
    }
    public void setLogStr(String log){
        logStr = log;
    }
    public String getTimeStamp(){
        return this.timeStr;
    }
    public String getLog(){
        return this.logStr;
    }

}
