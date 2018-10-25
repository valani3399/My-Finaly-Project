package com.example.softices.pankajtrainee.activity;

import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Build;
import android.provider.ContactsContract;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.softices.pankajtrainee.R;
import com.example.softices.pankajtrainee.adapter.ContextAdepter;
import com.example.softices.pankajtrainee.db.DbHelper;
import com.example.softices.pankajtrainee.models.ContextModel;

import java.util.ArrayList;
import java.util.List;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_CONTACTS;

public class ContentproviderActivity extends AppCompatActivity {
    private RecyclerView redemo;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<String> data;
    TextView tvcontextname;
    private ArrayList<String> permissionsToRequest;
    private ArrayList<String> permissionsRejected = new ArrayList<>();
    private ArrayList<String> permissions = new ArrayList<>();
    private final static int ALL_PERMISSIONS_RESULT = 107;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contentprovider);
        tvcontextname = (TextView) findViewById(R.id.tv_contextname);
        redemo = (RecyclerView) findViewById(R.id.recycler_contex);

        permissions.add(READ_CONTACTS);
        permissions.add(WRITE_CONTACTS);
        permissionsToRequest = findUnAskedPermissions(permissions);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest.size() > 0)
                requestPermissions(permissionsToRequest.toArray(new String[permissionsToRequest.size()]), ALL_PERMISSIONS_RESULT);
        } else if (hasPermission("android.permission.READ_CONTACTS")) {
            getAllContacts();
        }


    }

    private void getAllContacts() {

        ArrayList<ContextModel> arrayList=new ArrayList();
        ContextModel contextModel;
        ContentResolver contentResolver=getContentResolver();
        Cursor cursor=contentResolver.query(ContactsContract.Contacts.CONTENT_URI,
                null,null,
                null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        if(cursor.getCount()>0){
            while (cursor.moveToNext()){
                int hasPhoneNUmber= Integer.parseInt(cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));
                if(hasPhoneNUmber>0){
                    String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    String name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    contextModel=new ContextModel();
                    contextModel.setName(name);

                //this cursor use for get mobail number
                    Cursor phoneCursor = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?",
                            new String[]{id},
                            null);
                    if (phoneCursor.moveToNext()) {
                        String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        contextModel.setNumber(phoneNumber);
                    }
                    phoneCursor.close();
                    arrayList.add(contextModel);
                }
            }
        }
        cursor.close();
        ContextAdepter contextAdepter=new ContextAdepter(arrayList,this);
        redemo.setLayoutManager(new LinearLayoutManager(this));
        redemo.setAdapter(contextAdepter);
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(ContentproviderActivity.this);
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
                                    Intent f = new Intent(ContentproviderActivity.this, Dashboard.class);
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
