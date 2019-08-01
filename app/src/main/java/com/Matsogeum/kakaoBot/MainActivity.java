package com.Matsogeum.kakaoBot;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

import com.squareup.leakcanary.LeakCanary;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class MainActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.main);

        final ToggleButton tbtn = (ToggleButton) findViewById(R.id.enableToggle);
        final Button rbtn = (Button) findViewById(R.id.reloadBtn);
        final Button lbtn = (Button) findViewById(R.id.logBtn);
        final Button sbtn = (Button) findViewById(R.id.saveBtn);
        final com.Matsogeum.kakaoBot.NewEditText edt = (com.Matsogeum.kakaoBot.NewEditText) findViewById(R.id.code);
        final SharedPreferences sp = getSharedPreferences("data", MODE_PRIVATE);
        final SharedPreferences.Editor spe = sp.edit();
        final Boolean status = sp.getBoolean("status", false);
        startService(new Intent(this, NotificationCollectorMonitorService.class));
        if (status)
            tbtn.setChecked(true);
        else
            tbtn.setChecked(false);

        File scriptDir = new File("/sdcard/Mbot");
        if (!scriptDir.exists()) scriptDir.mkdirs();
        File script = new File("/sdcard/Mbot/response.js");
        if(!script.exists()) {
            try {
                OutputStream os = new FileOutputStream("/sdcard/Mbot/response.js");
                InputStream is = getResources().openRawResource(R.raw.response);
                int read = 0;
                byte[] bytes = new byte[1024];

                while ((read = is.read(bytes)) != -1) {
                    os.write(bytes, 0, read);
                }
                os.close();
                is.close();

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        edt.setText(FileStream.read("/sdcard/Mbot/response.js"));
        tbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spe.putBoolean("status", tbtn.isChecked());
                spe.commit();
            }
        });

        rbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //pass

            }
        });
        lbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LogActivity.class));

            }
        });
        sbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FileStream.write("/sdcard/Mbot/response.js",edt.getText().toString());
            }
        });

    }

    
}
