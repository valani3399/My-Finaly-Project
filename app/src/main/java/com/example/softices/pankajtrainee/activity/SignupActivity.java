package com.example.softices.pankajtrainee.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softices.pankajtrainee.R;
import com.example.softices.pankajtrainee.db.DbHelper;
import com.example.softices.pankajtrainee.methods.Utils;

import java.io.InputStream;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener {

    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();

    TextView tvsingin, tvnotragidter;
    CircleImageView circleImageView;
    Button btnsingup;
    EditText edtfname, edtlname, edtmobail, edtadress, edtemail, edtpassword, edtconformpassword;
    RadioGroup radioGroup;
    private DbHelper dbHelper;
    private String selectedRadioButtonText;
    private String Password;
    private String conformpassword;
    private String email;
    private static final int SELECT_PICTURE = 100;
    static final int REQUEST_IMAGE_CAPTURE = 19;
    private final static int ALL_PERMISSIONS_RESULT = 107;
    private CircleImageView imgUser;
    public byte[] inputImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_singup);
        tvnotragidter = (TextView) findViewById(R.id.tv_singin);
        tvsingin = (TextView) findViewById(R.id.tv_singin);
        edtfname = (EditText) findViewById(R.id.et_fname);
        edtlname = (EditText) findViewById(R.id.et_lname);
        edtmobail = (EditText) findViewById(R.id.et_mobail);
        edtadress = (EditText) findViewById(R.id.et_adress);
        edtemail = (EditText) findViewById(R.id.et_emil);
        edtpassword = (EditText) findViewById(R.id.et_password);
        radioGroup = (RadioGroup) findViewById(R.id.rg_gender);
        btnsingup = (Button) findViewById(R.id.btn_signup);
        edtconformpassword = (EditText) findViewById(R.id.et_conpassword);
        imgUser = (CircleImageView) findViewById(R.id.tv_i);

        dbHelper = new DbHelper(getApplicationContext());
        btnsingup.setOnClickListener(this);
        imgUser.setOnClickListener(this);
        tvnotragidter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent(SignupActivity.this, SigninActivity.class);
                startActivity(f);
                finish();
            }
        });

        edtemail.setText("aaa@aaa.aaa");
        edtpassword.setText("aaaaaa");
        edtconformpassword.setText("aaaaaa");
        edtfname.setText("aaa");
        edtlname.setText("aaa");
        edtmobail.setText("1234567890");
        edtadress.setText("aaaaaaaaaaaaaaaaaaaa");

        Bitmap icon = BitmapFactory.decodeResource(getResources(),R.drawable.a);
        inputImage=Utils.getImageBytes(icon);

        permissions.add(CAMERA);
        permissions.add(READ_EXTERNAL_STORAGE);
        permissionsToRequest = findUnAskedPermissions(permissions);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_signup:
                Password = edtpassword.getText().toString();
                conformpassword = edtconformpassword.getText().toString();
                email = edtemail.getText().toString();

                int selectedRadioButtonID = radioGroup.getCheckedRadioButtonId();
                if (selectedRadioButtonID != -1) {
                    RadioButton selectedRadioButton = (RadioButton) findViewById(selectedRadioButtonID);
                    selectedRadioButtonText = selectedRadioButton.getText().toString();
                }
                if (!isValidPhone(edtmobail.getText().toString())) {
                    Toast.makeText(getApplicationContext(), "Phone number is not valid", Toast.LENGTH_SHORT).show();
                }
                if (edtfname.getText().toString().isEmpty()) {
                    Toast.makeText(this, "enter fname", Toast.LENGTH_SHORT).show();
                } else if (edtlname.getText().toString().isEmpty()) {
                    Toast.makeText(this, "enter lname", Toast.LENGTH_SHORT).show();
                } else if (edtmobail.getText().toString().isEmpty()) {
                    Toast.makeText(this, "enter mobail no", Toast.LENGTH_SHORT).show();
                } else if (edtadress.getText().toString().isEmpty()) {
                    Toast.makeText(this, "enter adress", Toast.LENGTH_SHORT).show();
                } else if (edtemail.getText().toString().isEmpty()) {
                    Toast.makeText(this, "enter email", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(email)) {
                    Toast.makeText(this, "enter valid email", Toast.LENGTH_SHORT).show();
                } else if (!isValidPass(Password)) {
                    Toast.makeText(getApplicationContext(), "Password must be more than 4 characters.", Toast.LENGTH_SHORT).show();
                } else if (!Password.equals(conformpassword)) {
                    Toast.makeText(getApplicationContext(), "Password not matched.", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isLogin = dbHelper.checkEmail(email);
                    if (isLogin) {
                        Toast.makeText(this, "this id is match", Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("image",""+inputImage);
                        boolean insert = dbHelper.inserRecord(
                                edtfname.getText().toString(),
                                edtlname.getText().toString(),
                                edtmobail.getText().toString(),
                                edtadress.getText().toString(),
                                edtemail.getText().toString(),
                                edtpassword.getText().toString(),
                                selectedRadioButtonText,
                                inputImage);
                        if (insert) {
                            Intent f = new Intent(SignupActivity.this, SigninActivity.class);
                            startActivity(f);
                            finish();
                        } else {
                            Toast.makeText(this, "not sucees", Toast.LENGTH_SHORT).show();
                        }

                    }
                }
                break;
        }
        imgUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (permissionsToRequest.size() > 0)
                        requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
                }
                final CharSequence[] items = {"Take Photo", "Choose from Library","Cancel"};
                AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        try {
            if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Bitmap imageBitmap = (Bitmap) extras.get("data");
                inputImage = Utils.getImageBytes(imageBitmap);
                imgUser.setImageBitmap(imageBitmap);
            } else if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    InputStream iStream = null;
                    iStream = getContentResolver().openInputStream(selectedImageUri);
                    inputImage = Utils.getBytes(iStream);
                    imgUser.setImageURI(selectedImageUri);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            Log.e("onActivityResult",  e + " ");
        }

    }

    public static boolean isValidEmail(String email) {
        return (!TextUtils.isEmpty((CharSequence) email) && Patterns.EMAIL_ADDRESS.matcher((CharSequence) email).matches());
    }

    private boolean isValidPass(String password) {
        if (password.length() >= 4)
            return true;
        else
            return false;
    }
    private boolean isValidPhone(String s) {
        if (s.length() == 10)
            return true;
        else
            return false;
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
    }

    private ArrayList<String> findUnAskedPermissions(ArrayList<String> wanted) {
        ArrayList<String> result = new ArrayList<String>();
//        String perm is position and wanted is size of array
        for (String perm : wanted) {
            if (!hasPermission(perm)) {
                result.add(perm);
            }
        }

        return result;
    }
    private boolean hasPermission(String permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return (checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case ALL_PERMISSIONS_RESULT:
                for (String perms : permissionsToRequest) {
                    if (hasPermission(perms)) {

                    } else {
                        permissionsRejected.add(perms);
                    }
                }
                if (permissionsRejected.size() > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected.get(0))) {
                            AlertDialog.Builder builder = new AlertDialog.Builder(SignupActivity.this);
                            builder.setMessage("Hello, you can hide this message by just tapping outside the dialog box!");
                            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

                                @RequiresApi(api = Build.VERSION_CODES.M)
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    requestPermissions(permissionsRejected.toArray(new String[permissionsRejected.size()]), ALL_PERMISSIONS_RESULT);
                                }
                            });
                            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    Intent f = new Intent(SignupActivity.this, SignupActivity.class);
                                    startActivity(f);
                                    finish();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();
                            return;
                        }
                    }
                }
                break;
        }

    }


}
