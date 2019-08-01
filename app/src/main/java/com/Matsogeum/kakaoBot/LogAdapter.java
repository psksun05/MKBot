package com.Matsogeum.kakaoBot;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class LogAdapter extends BaseAdapter {
    private ArrayList<LogItem> logItemList=new ArrayList<LogItem>();

    public LogAdapter(){

    }

    @Override
    public int getCount() {
        return logItemList.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parents) {
        final int pos = position;
        final Context context = parents.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.log_item, parents, false);
        }

        TextView timeTextView = (TextView)convertView.findViewById(R.id.timeStamp);
        TextView logTextView = (TextView)convertView.findViewById(R.id.Log);
        LogItem logItem = logItemList.get(position);

        timeTextView.setText(logItem.getTimeStamp());
        logTextView.setText(logItem.getLog());

        return convertView;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public Object getItem(int i) {
        return logItemList.get(i);
    }
    public void addLog(String str){
        LogItem item = new LogItem();
        Date date=new Date();
        SimpleDateFormat sdf=new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]", Locale.KOREAN);
        item.setTimeStr(sdf.format(date));
        item.setLogStr(str);
        logItemList.add(item);
    }
}

