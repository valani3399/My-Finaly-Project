package com.example.softices.pankajtrainee.activity;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.softices.pankajtrainee.R;

public class GalleryActivity extends AppCompatActivity implements View.OnClickListener {

    private static final int SELECT_PICTURE = 100;
    private static final String TAG = "MainActivity";
    TextView camera, choosegally, cancle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        choosegally = (TextView) findViewById(R.id.tv_choosegally);
        camera = (TextView) findViewById(R.id.tv_takephoto);
        cancle = (TextView) findViewById(R.id.tv_cancle);
        cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
                startActivityForResult(intent, 0);
            }
        });
        choosegally.setOnClickListener(this);
    }

    public void onClick(View v) {
        openImageChooser();
    }

    /* Choose an image from Gallery */
    void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                // Get the url from data
                Uri abc = data.getData();

                if (null != abc) {
                    // Get the path from the Uri
                    String path = getPathFromURI(abc);
                    Log.i(TAG, "Image Path : " + path);
                    // Set the image in ImageView
                    ((ImageView) findViewById(R.id.iv_image)).setImageURI(abc);
                }
            }
        }
    }

    /* Get the real path from the URI */
    public String getPathFromURI(Uri contentUri) {
        String res = null;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            res = cursor.getString(column_index);
        }
        cursor.close();
        return res;
    }


//   TextView camera,choosegally,cancle;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_gallery);
//        choosegally=(TextView)findViewById(R.id.tv_choosegally);
//        camera=(TextView)findViewById(R.id.tv_takephoto);
//        cancle=(TextView)findViewById(R.id.tv_cancle);
//        camera.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE_SECURE);
//                startActivityForResult(intent,0);
//            }
//
//        });
//        choosegally.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setType("image/*");
//                intent.setAction(Intent.ACTION_GET_CONTENT);
//                startActivityForResult(intent,0);
//            }
//        });
//        cancle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent f = new Intent(GalleryActivity.this, ProfileActivity.class);
//                startActivity(f);
//                finish();
//            }
//        });
//
//    }
}
