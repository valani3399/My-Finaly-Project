package com.example.softices.pankajtrainee.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.softices.pankajtrainee.R;
import com.example.softices.pankajtrainee.db.DbHelper;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

public class SigninActivity extends AppCompatActivity {
    TextView tvforgotpassward, tvragister,tv;
    EditText edtEmail, edtpassword;
    Button singin;
    private DbHelper dbHelper;
    private String eemail;
    private String password;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        edtpassword = (EditText) findViewById(R.id.edt_password);
        edtEmail = (EditText) findViewById(R.id.tex_emil);
        tvforgotpassward = (TextView) findViewById(R.id.tv_forgotpassward);
        tvragister = (TextView) findViewById(R.id.tv_notragister);
        singin = (Button) findViewById(R.id.btn_signin);
        tv=(TextView)findViewById(R.id.tv_email_error);
        dbHelper = new DbHelper(getApplicationContext());

        edtEmail.setText("aaa@aaa.aaa");
        edtpassword.setText("aaaaaa");
        singin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                subscribeToPushService();
                password = edtpassword.getText().toString();
                int size = password.length();
                eemail = edtEmail.getText().toString();
                if (edtEmail.getText().toString().isEmpty()) {
                    tv.setVisibility(View.VISIBLE);
//                    Toast.makeText(SigninActivity.this, "enter email id", Toast.LENGTH_SHORT).show();
                } else if (!isValidEmail(eemail)) {
                    Toast.makeText(SigninActivity.this, "enter valid email id", Toast.LENGTH_SHORT).show();
                } else if (edtpassword.getText().toString().isEmpty()) {
                    Toast.makeText(SigninActivity.this, "enter password", Toast.LENGTH_SHORT).show();
                } else if (!(size > 3)) {
                    Toast.makeText(SigninActivity.this, "enter more then 4 digit", Toast.LENGTH_SHORT).show();
                } else {
                    boolean isLogin = dbHelper.checkEmailPasasword(eemail, password);
                    if (isLogin) {
                        shareprefer(SigninActivity.this,true,eemail);
                        startActivity(new Intent(SigninActivity.this, Dashboard.class));
                    } else {
                        Toast.makeText(SigninActivity.this, "Email & Password is not valid.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            private boolean isValidEmail(String eemail) {
                return (!TextUtils.isEmpty((CharSequence) eemail) &&
                        Patterns.EMAIL_ADDRESS.matcher((CharSequence) eemail).matches());
            }

        });

        tvforgotpassward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent f = new Intent(SigninActivity.this, ForgotPasswordActivity.class);
                startActivity(f);
                finish();
            }
        });

        tvragister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent f = new Intent(SigninActivity.this, SignupActivity.class);
                startActivity(f);
                finish();
            }


        });
    }
//    private void subscribeToPushService() {
//        FirebaseMessaging.getInstance().subscribeToTopic("news");
//
//        Log.d("AndroidBash", "Subscribed");
//        Toast.makeText(SigninActivity.this, "Subscribed", Toast.LENGTH_SHORT).show();
//
//        String token = FirebaseInstanceId.getInstance().getToken();
//
//        // Log and toast
//        Log.d("AndroidBash", token);
//        Toast.makeText(SigninActivity.this, token, Toast.LENGTH_SHORT).show();
//    }
    public static  void shareprefer(Context context,boolean login,String gmail){
        SharedPreferences sharedPreferences= PreferenceManager.getDefaultSharedPreferences(context );
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putBoolean("is_login",login);
        editor.putString("gmailKey",gmail);
        editor.apply();



    }
}
