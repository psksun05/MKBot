package com.Matsogeum.kakaoBot;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class LogActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.log);
        ListView listview =(ListView)findViewById(R.id.logListView);
        listview.setAdapter(Log.adapter);

    }
}
