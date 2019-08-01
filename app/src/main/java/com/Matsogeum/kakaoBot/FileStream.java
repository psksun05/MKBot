package com.Matsogeum.kakaoBot;

import org.mozilla.javascript.ScriptableObject;
import org.mozilla.javascript.annotations.JSStaticFunction;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class FileStream extends ScriptableObject {
    @JSStaticFunction
    public static void write(String path,String content){
        try{
            File file=new java.io.File(path);
            OutputStreamWriter os = new java.io.OutputStreamWriter(new FileOutputStream(file),"UTF-8");
            BufferedWriter bw = new java.io.BufferedWriter(os);
            bw.write(content);
            bw.close();
            os.close();
        }catch (Exception e){
            e.printStackTrace();
            Log.d(e.toString());
        }
    }
    @JSStaticFunction
    public static String read(String path){
        String s;
        try{
            File file=new java.io.File(path);
            if(!file.exists()) return "";
            InputStream fis = new java.io.FileInputStream(file);
            InputStreamReader isr = new java.io.InputStreamReader(fis);
            BufferedReader br = new java.io.BufferedReader(isr);
            s=br.readLine();
            String read="";
            while ((read=br.readLine())!=null) s+="\n"+read;
            fis.close();
            isr.close();
            br.close();
            return s;
        }catch (Exception e){
            e.printStackTrace();
            Log.d(e.toString());
            return "";
        }
    }

    @Override
    public String getClassName() {
        return "FileStream";
    }
}
