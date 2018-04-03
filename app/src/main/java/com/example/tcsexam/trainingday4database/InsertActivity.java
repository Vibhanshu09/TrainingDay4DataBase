package com.example.tcsexam.trainingday4database;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class InsertActivity extends AppCompatActivity {

    EditText name, mobile, email, cnfEmail;
    Button insertDataButton;
    SQLiteDatabase db;
    UserDbHelper userDbHelper;
    Context context=this;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);

        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        email = findViewById(R.id.email);
        cnfEmail = findViewById(R.id.cnfEmail);
        insertDataButton = findViewById(R.id.insert_submit);

        insertDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveData();
            }
        });
    }

    public void saveData(){
        String mName, mMobile, mEmail;
        mName = name.getText().toString().trim();
        mMobile = mobile.getText().toString().trim();
        if (email.getText().toString().trim().toLowerCase().equals(cnfEmail.getText().toString().trim().toLowerCase()))
            mEmail = email.getText().toString().trim();
        else {
            cnfEmail.setError("Please Enter correct email");
            return;
        }
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
            userDbHelper.addInformation(mName, mMobile, mEmail, db);
            Toast.makeText(context, "Data Saved Successfully!", Toast.LENGTH_SHORT).show();
            closeDatabase();
        }
    }

    private void clearForm(){
        name.setText("");
        mobile.setText("");
        email.setText("");
        cnfEmail.setText("");
    }

    private void closeDatabase(){
        userDbHelper.close();
        Log.e("Database:", "Closed");
    }
}
