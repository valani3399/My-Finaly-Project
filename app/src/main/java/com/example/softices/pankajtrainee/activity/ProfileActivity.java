package com.example.softices.pankajtrainee.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.softices.pankajtrainee.R;
import com.example.softices.pankajtrainee.db.DbHelper;
import com.example.softices.pankajtrainee.methods.Utils;

import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProfileActivity extends AppCompatActivity {

    private DbHelper dbHelper;
    public static final String MyPREFERENCES = "MyPrefs";
    public Cursor cursor;
    EditText fname, lname, mobailno, address, emailp;
    RadioGroup radioGroup;
    RadioButton rbMale, rbFemale;
    Button update;
    CircleImageView imageView;
    public Bitmap bitmap;
    private static final int SELECT_PICTURE = 100;
    static final int REQUEST_IMAGE_CAPTURE = 19;
    public byte[] inputImage;
    private String strFName, strLName, strmobail, stradd, strgendar = "", stremail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        dbHelper = new DbHelper(getApplicationContext());
        fname = (EditText) findViewById(R.id.et_fname);
        lname = (EditText) findViewById(R.id.et_lname);
        mobailno = (EditText) findViewById(R.id.et_mobailno);
        address = (EditText) findViewById(R.id.et_adress);
        radioGroup = (RadioGroup) findViewById(R.id.rg_radiogroup);
        rbMale = (RadioButton) findViewById(R.id.rb_male);
        rbFemale = (RadioButton) findViewById(R.id.rb_female);
        update = (Button) findViewById(R.id.btn_update);
        emailp = (EditText) findViewById(R.id.et_emailp);
        imageView = (CircleImageView) findViewById(R.id.iv_image);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("PROFILE");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent(ProfileActivity.this, Dashboard.class);
                startActivity(f);

            }
        });
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ProfileActivity.this);
        String email = sharedPreferences.getString("gmailKey", "");
        cursor = dbHelper.getUserData(email);
        if (cursor.moveToFirst()) {
            do {
                strFName = cursor.getString(cursor.getColumnIndex(DbHelper.FNAME));
                strLName = cursor.getString(cursor.getColumnIndex(DbHelper.LNAME));
                stradd = cursor.getString(cursor.getColumnIndex(DbHelper.ADDRESS));
                strmobail = cursor.getString(cursor.getColumnIndex(DbHelper.MOBAILNO));
                strgendar = cursor.getString(cursor.getColumnIndex(DbHelper.CATEGORY));
                stremail = cursor.getString(cursor.getColumnIndex(DbHelper.MAILID));
                byte[] decodedString = cursor.getBlob(cursor.getColumnIndex(DbHelper.IMAGE));
                bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            } while (cursor.moveToNext());
        }
        cursor.close();
        fname.setText(strFName);
        lname.setText(strLName);
        mobailno.setText(strmobail);
        address.setText(stradd);
        emailp.setText(stremail);
        imageView.setImageBitmap(bitmap);
        if (strgendar.equals("male")) {
            rbMale.setChecked(true);
        } else {
            rbFemale.setChecked(true);
        }
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fname.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please enter fname", Toast.LENGTH_SHORT).show();

                } else if (lname.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please enter lname", Toast.LENGTH_SHORT).show();

                } else if (mobailno.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please enter mobail no", Toast.LENGTH_SHORT).show();
                } else if (address.getText().toString().isEmpty()) {
                    Toast.makeText(ProfileActivity.this, "Please enter adress", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isupdate = dbHelper.updateRecord(fname.getText().toString(),
                            lname.getText().toString(),
                            mobailno.getText().toString(),
                            address.getText().toString(),
                            emailp.getText().toString(),
                            inputImage);
                    if (isupdate) {
                        Toast.makeText(ProfileActivity.this, "UPDATE sucessfully", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProfileActivity.this, "Not UPDATE ", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"Take Photo", "Choose from Library", "Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(ProfileActivity.this);
                builder.setTitle("Add Photo!");
                builder.setItems(items, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int item) {
                        if (items[item].equals("Take Photo")) {
                            dispatchTakePictureIntent();

                        } else if (items[item].equals("Choose from Library")) {
                            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(intent, 100);
                        } else if (items[item].equals("Cancel")) {
                            dialog.dismiss();
                        }
                    }
                });
                builder.show();
            }
        });
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                inputImage = Utils.getImageBytes(imageBitmap);
                imageView.setImageBitmap(imageBitmap);
            } else if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    InputStream iStream = null;
                    iStream = getContentResolver().openInputStream(selectedImageUri);
                    inputImage = Utils.getBytes(iStream);
                    imageView.setImageURI(selectedImageUri);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("onActivityResult", e + "");
        }
    }
}
