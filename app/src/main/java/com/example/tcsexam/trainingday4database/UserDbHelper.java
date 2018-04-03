package com.example.tcsexam.trainingday4database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by Vibhanshu on 4/2/2018.
 */

public class UserDbHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "userdb.db";
    public static final int DATABASE_VERSION = 1;
    public static final String CREATE_QUERY =
            "CREATE TABLE " + UserDetail.UserInfo.TABLE + "(" +
                    UserDetail.UserInfo.USER_NAME + " TEXT, " +
                    UserDetail.UserInfo.USER_MOB + " TEXT, " +
                    UserDetail.UserInfo.USER_EMAIL + " TEXT);";

    public UserDbHelper(Context context){
        super(context, DATABASE_NAME,null, DATABASE_VERSION);
        Log.e("Database Operation","Database Created | Opened");
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_QUERY);
        Log.e("Database Operation","Table Created");

    }

    public void addInformation(String name, String mob, String email, SQLiteDatabase sqLiteDatabase){
        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDetail.UserInfo.USER_NAME,name);
        contentValues.put(UserDetail.UserInfo.USER_MOB,mob);
        contentValues.put(UserDetail.UserInfo.USER_EMAIL,email);
        sqLiteDatabase.insert(UserDetail.UserInfo.TABLE,null, contentValues);
        Log.e("Database Operation","Info Inserted");
    }

    public Cursor searchData(String searchName, SQLiteDatabase sqLiteDatabase){
        String[] column_names = {UserDetail.UserInfo.USER_MOB, UserDetail.UserInfo.USER_EMAIL};
        String selection_clause = UserDetail.UserInfo.USER_NAME + " LIKE ?";
        String[] selection_args = {searchName};
        Cursor cursor = sqLiteDatabase.query(
                UserDetail.UserInfo.TABLE,
                column_names,
                selection_clause,
                selection_args,
                null,
                null,
                null);
        return cursor;
    }
    public void deleteData(String deleteName, SQLiteDatabase sqLiteDatabase){
        String selection_clause = UserDetail.UserInfo.USER_NAME + " LIKE ?";
        String[] selection_args = {deleteName};
        sqLiteDatabase.delete(UserDetail.UserInfo.TABLE,selection_clause,selection_args);
    }
    public int updateData(String oldName, String newName, String newMobile, String newEmail, SQLiteDatabase sqLiteDatabase){

        ContentValues contentValues = new ContentValues();
        contentValues.put(UserDetail.UserInfo.USER_NAME,newName);
        contentValues.put(UserDetail.UserInfo.USER_MOB,newMobile);
        contentValues.put(UserDetail.UserInfo.USER_EMAIL,newEmail);
        String selection_clause = UserDetail.UserInfo.USER_NAME + " Like ?";
        String[] selection_args = {oldName};
        int count = sqLiteDatabase.update(UserDetail.UserInfo.TABLE,contentValues,selection_clause,selection_args);
        return count;
    }
    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
