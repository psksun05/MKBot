package com.Matsogeum.kakaoBot;

import android.graphics.Bitmap;
import android.util.Base64;


import java.io.ByteArrayOutputStream;

public class ImageDB {
    private static Bitmap profileImage=null;


    public ImageDB (Bitmap profileImage){
        super();
        this.profileImage = profileImage;
    }

    public static String getProfileImage(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        profileImage.compress(Bitmap.CompressFormat.PNG,100,baos);
        byte[] ba=baos.toByteArray();
        return Base64.encodeToString(ba, Base64.DEFAULT);
    }

    public static Bitmap getProfileImageBitmap(){
        return profileImage;
    }

}
