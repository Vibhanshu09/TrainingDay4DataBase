package com.example.tcsexam.trainingday4database;

import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity {

    LinearLayout showResultLayout;
    EditText searchQueryName;
    TextView yourName, yourEmail, yourMobile;
    Button searchNameButton, deleteButton;
    String searchQuery;
    SQLiteDatabase db;
    UserDbHelper userDbHelper;
    Context context=this;
    AlertDialog.Builder builder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        showResultLayout = findViewById(R.id.show_result_layout);
        searchQueryName = findViewById(R.id.search_query_name);
        searchNameButton = findViewById(R.id.search_name);
        deleteButton = findViewById(R.id.delete_data);
        yourName = findViewById(R.id.your_name);
        yourEmail = findViewById(R.id.your_email);
        yourMobile = findViewById(R.id.your_mobile);

        searchNameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateSearchQueryField()){
                    userDbHelper = new UserDbHelper(context);
                    db = userDbHelper.getReadableDatabase();
                    Cursor cursor = userDbHelper.searchData(searchQuery,db);
                    if (cursor.moveToFirst()){
                        yourName.setText(searchQuery);
                        yourEmail.setText(cursor.getString(1));
                        yourMobile.setText(cursor.getString(0));
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
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                userDbHelper = new UserDbHelper(context);
                db = userDbHelper.getWritableDatabase();
                userDbHelper.deleteData(searchQuery,db);
                builder = new AlertDialog.Builder(context);
                builder.setTitle("Success");
                builder.setMessage("Data Deleted...");
                builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        resetLayout();
                        dialogInterface.dismiss();

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                db.close();
            }
        });

    }
    private void resetLayout(){
        searchQuery="";
        searchQueryName.setText("");
        showResultLayout.setVisibility(View.GONE);

    }
    private boolean validateSearchQueryField(){
        if (!searchQueryName.getText().toString().trim().isEmpty()){
            searchQuery = searchQueryName.getText().toString().trim();
            return true;
        }
        else {
            searchQueryName.setError("Enter a Name To search");
            return false;
        }
    }
}
