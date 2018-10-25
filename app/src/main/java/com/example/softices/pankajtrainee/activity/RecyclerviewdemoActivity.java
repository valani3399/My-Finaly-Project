package com.example.softices.pankajtrainee.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.softices.pankajtrainee.R;
import com.example.softices.pankajtrainee.adapter.itemAdapter;
import com.example.softices.pankajtrainee.db.DbHelper;
import com.example.softices.pankajtrainee.models.AppModel;

import java.util.ArrayList;
import java.util.List;

public class RecyclerviewdemoActivity extends AppCompatActivity {

    private RecyclerView redemo;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<String> data;
    private DbHelper dbHelper;
    public static final String MyPREFERENCES = "MyPrefs";
    public Cursor cursor;
    private String strFName,strGmail;
//    TextView tvname;



    @Override
    public void onPanelClosed(int featureId, Menu menu) {
        super.onPanelClosed(featureId, menu);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recyclerviewdemo);
//        tvname = (TextView) findViewById(R.id.tv_name);
        redemo = (RecyclerView) findViewById(R.id.recyler_view);
        dbHelper = new DbHelper(getApplicationContext());
        linearLayoutManager = new LinearLayoutManager(this);
        redemo.setLayoutManager(linearLayoutManager);
        Toolbar toolbar=findViewById(R.id.toolbar);
        setTitle("PROFILE");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("USER FIRST NAME");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent f = new Intent(RecyclerviewdemoActivity.this, Dashboard.class);
                startActivity(f);

            }
        });



        ArrayList<AppModel> data = new ArrayList<>();
        final SharedPreferences sharedPreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        String email = sharedPreferences.getString("gmailKey", "");
        cursor = dbHelper.getUsersList();
        if (cursor.moveToFirst()) {
            do {
                AppModel appModel = new AppModel();
                strFName = cursor.getString(cursor.getColumnIndex(DbHelper.FNAME));
                strGmail=cursor.getString(cursor.getColumnIndex(DbHelper.MAILID));
                appModel.setEmail(strGmail);
                appModel.setFirstName(strFName);
                data.add(appModel);
            } while (cursor.moveToNext());
        }
        cursor.close();

        itemAdapter itemAdapter = new itemAdapter(this,dbHelper,data);
        redemo.setAdapter(itemAdapter);
    }
}
