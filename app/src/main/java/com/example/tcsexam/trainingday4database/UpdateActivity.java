package com.example.tcsexam.trainingday4database;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {

    EditText name, mobile, email, updateName;
    Button searchDataButton, updateDataButton;
    LinearLayout showResultLayout;
    String searchQuery;
    SQLiteDatabase db;
    UserDbHelper userDbHelper;
    Context context=this;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        name = findViewById(R.id.new_name);
        mobile = findViewById(R.id.new_mobile);
        email = findViewById(R.id.new_email);
        updateName = findViewById(R.id.update_name);
        showResultLayout = findViewById(R.id.show_result_layout);
        searchDataButton = findViewById(R.id.update_name_button);
        updateDataButton = findViewById(R.id.update_details);

        searchDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateSearchQueryField()){
                    userDbHelper = new UserDbHelper(context);
                    db = userDbHelper.getReadableDatabase();
                    Cursor cursor = userDbHelper.searchData(searchQuery,db);
                    if (cursor.moveToFirst()){
                        name.setText(searchQuery);
                        mobile.setText(cursor.getString(0));
                        email.setText(cursor.getString(1));
                        showResultLayout.setVisibility(View.VISIBLE);
                    }else {
                        builder = new AlertDialog.Builder(context);
                        builder.setTitle("Error");
                        builder.setMessage("Data Not Found...");
                        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.dismiss();
                            }
                        });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }
                    cursor.close();
                    db.close();
                }
            }
        });
        updateDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateData();
            }
        });
    }
    public void updateData(){
        String mName, mMobile, mEmail;
        mName = name.getText().toString().trim();
        mMobile = mobile.getText().toString().trim();
        mEmail = email.getText().toString().trim();
        if (mName.equals("") || mMobile.equals("") || mEmail.equals("")){
            builder = new AlertDialog.Builder(context);
            builder.setTitle("Warning");
            builder.setMessage("Please Fill All Fileds");
            builder.setPositiveButton("Got It", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss();
                }
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
        else {
            userDbHelper = new UserDbHelper(context);
            db = userDbHelper.getWritableDatabase();
            userDbHelper.updateData(searchQuery,mName, mMobile, mEmail, db);
            Toast.makeText(context, "Data Updated Successfully!", Toast.LENGTH_SHORT).show();
            clearForm();
            closeDatabase();
        }
    }
    private boolean validateSearchQueryField(){
        if (!updateName.getText().toString().trim().isEmpty()){
            searchQuery = updateName.getText().toString().trim();
            return true;
        }
        else {
            updateName.setError("Enter a Name To search");
            return false;
        }
    }
    private void clearForm(){
        name.setText("");
        mobile.setText("");
        email.setText("");
        updateName.setText("");
    }
    private void closeDatabase(){
        userDbHelper.close();
        Log.e("Database:", "Closed");
    }
}
