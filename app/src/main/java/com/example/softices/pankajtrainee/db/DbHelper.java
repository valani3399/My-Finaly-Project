package com.example.softices.pankajtrainee.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import com.example.softices.pankajtrainee.models.AppModel;

public class DbHelper extends SQLiteOpenHelper {
    static Context context;

    public static String DATABASE_NAME = "EmployeeRecords";

    public static final String TABLE1_NAME = "employee";
    public static final String PASSWORD = "password";
    public static final String FNAME = "fname";
    public static final String LNAME = "lname";
    public static final String MOBAILNO = "mobailno";
    public static final String ADDRESS = "address";
    public static final String MAILID = "mailid";
    public static final String CATEGORY = "category";
    public static final String IMAGE = "image";


    public String CREATE_TABLE1 = "CREATE TABLE " + TABLE1_NAME + "(" +
            PASSWORD + " TEXT , " +
            FNAME + " TEXT , " +
            LNAME + " TEXT , " +
            MOBAILNO + " NUMBER , " +
            ADDRESS + " TEXT , " +
            MAILID + " TEXT PRIMARY KEY, " +
            CATEGORY + " TEXT , " +
            IMAGE + " BLOB)";

    private String DROP_USER_TABLE = "DROP TABLE IF EXISTS " + TABLE1_NAME;

    public DbHelper(Context context) {
        super(context, context.getExternalFilesDir(null).getAbsolutePath()
                + "/" + DATABASE_NAME, null, 1);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE1);
        Log.e("Table is created","....................!");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DROP_USER_TABLE);
        onCreate(db);
    }

    public boolean inserRecord(String fname, String lname, String mobailno, String address,
                               String mailid, String password, String category, byte[] image) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues cValues = new ContentValues();
            cValues.put(FNAME, fname);
            cValues.put(LNAME, lname);
            cValues.put(MOBAILNO, mobailno);
            cValues.put(ADDRESS, address);
            cValues.put(MAILID, mailid);
            cValues.put(PASSWORD, password);
            cValues.put(CATEGORY, category);
            cValues.put(IMAGE, image);
            db.insert(TABLE1_NAME, null, cValues);
            db.close();
            Log.e(TABLE1_NAME, "created---------");
            return true;
        } catch (Exception e) {
            Log.e("inserRecord", e + "");
        }
        return false;
    }

    public boolean updateRecord(String fname, String lname, String mobailno,
                                String address, String mailid,byte[] image) {
        try {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues cValues = new ContentValues();
            cValues.put(FNAME, fname);
            cValues.put(LNAME, lname);
            cValues.put(MOBAILNO, mobailno);
            cValues.put(ADDRESS, address);
            cValues.put(MAILID, mailid);
            cValues.put(IMAGE,image);
            //cValues.put(PASSWORD, password);
            //cValues.put(CATEGORY, category);
            String selection = MAILID + " = ?";
            String[] selectionArgs = {mailid};
            db.update(TABLE1_NAME, //Table to query
                    cValues,                    //columns to return
                    selection,                  //columns for the WHERE clause
                    selectionArgs);
            db.close();
            Log.e(TABLE1_NAME, "updated---------");
            return true;
        } catch (Exception e) {
            Log.e("updateRecord", e + "");
        }
        return false;
    }

    public Boolean deleteUser(String user) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.delete(TABLE1_NAME, MAILID + " = ?",
                    new String[]{user});
            db.close();
            return true;
        } catch (Exception e) {
            Log.e("DELET RECORD", e + "");
            return false;
        }
    }

    public Boolean checkEmailPasasword(String email, String password) {
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor = dataBase.rawQuery("select * from " + TABLE1_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                String strEmail = cursor.getString(cursor.getColumnIndex(DbHelper.MAILID));
                String strPass = cursor.getString(cursor.getColumnIndex(DbHelper.PASSWORD));
                Log.e("email...",""+strEmail);
                Log.e("password...",""+strPass);
                if (strEmail.equals(email) && strPass.equals(password)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }


    public Boolean checkEmail(String email) {
        SQLiteDatabase dataBase = getWritableDatabase();
        Cursor cursor = dataBase.rawQuery("select * from " + TABLE1_NAME, null);
        if (cursor.moveToFirst()) {
            do {
                String strEmail = cursor.getString(cursor.getColumnIndex(DbHelper.MAILID));
                if (strEmail.equals(email)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return false;
    }public Cursor getUserData(String email) {

        // array of columns to fetch
        String[] columns = {
                DbHelper.MAILID,
                DbHelper.FNAME,
                DbHelper.LNAME,
                DbHelper.MOBAILNO,
                DbHelper.ADDRESS,
                DbHelper.CATEGORY,
                DbHelper.IMAGE
        };
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = MAILID + " = ?";
        String[] selectionArgs = {email};
        Cursor cursor = db.query(TABLE1_NAME, //Table to query
                columns,                    //columns to return
                selection,                  //columns for the WHERE clause
                selectionArgs,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        return cursor;
    }

    public Cursor getUsersList() {

        // array of columns to fetch
        String[] columns = {
                DbHelper.MAILID,
                DbHelper.FNAME,
                DbHelper.LNAME,
                DbHelper.MOBAILNO,
                DbHelper.ADDRESS,
                DbHelper.CATEGORY
        };
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE1_NAME, //Table to query
                columns,                    //columns to return
                null,                  //columns for the WHERE clause
                null,              //The values for the WHERE clause
                null,                       //group the rows
                null,                      //filter by row groups
                null);                      //The sort order
        return cursor;
    }
}