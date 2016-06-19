package com.yuewawa.simpleaccount.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by yuewawa on 2016-06-14.
 */
public class FileUtil {

    public static byte[] getFileFromAssets(Context context, String filePath){
        byte[] bytes = null;
        byte[] buf;
        int len;
        ByteArrayOutputStream baos;
        try {
            InputStream is = context.getAssets().open(filePath);
            int size = is.available();
            buf = new byte[size];
            baos = new ByteArrayOutputStream();
            while ((len=is.read(buf))!=-1){
                baos.write(buf, 0, len);
            }
            bytes = baos.toByteArray();
            baos.close();
            is.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bytes;
    }

    public static Bitmap byteArrayToBitmap(byte[] b){
        Bitmap bitmap = null;
        if (b.length!=0){
            bitmap = BitmapFactory.decodeByteArray(b, 0, b.length);
        }
        return bitmap;
    }

    public static byte[] bitmapToByteArray(Bitmap bitmap){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        return baos.toByteArray();
    }
}
