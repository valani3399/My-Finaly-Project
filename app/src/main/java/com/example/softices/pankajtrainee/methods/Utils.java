package com.example.softices.pankajtrainee.methods;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class Utils {

    private static ProgressDialog progress;

    public static byte[] getImageBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream;
        stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }

    public static Bitmap getImage(byte[] image) {
        return BitmapFactory.decodeByteArray(image, 0, image.length);
    }

    public static byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;  //dife size
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    public static void showProgressDialog(Context context) {
        if (progress == null) {
            progress = new ProgressDialog(context);
            progress.setMessage("Loading..");
            //tought but not progress dissmiss
            progress.setCancelable(false);
            progress.setProgress(0);
            progress.show();
        }
    }

    public static void hideProgressDialog() {
        if (progress != null && progress.isShowing()) {
            progress.dismiss();
            progress = null;
        }
    }
}