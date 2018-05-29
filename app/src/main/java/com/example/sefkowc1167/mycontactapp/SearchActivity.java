package com.example.sefkowc1167.mycontactapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        Log.d("MyContactApp", "SearchActivity: entered");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);

        //Get the intent that initiated this activity
        Intent intent = getIntent();
        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        //Capture the layout's textView and set the string as the text
        TextView textView = findViewById(R.id.textView2);
        textView.setText("Searched for " + message);



    }
}
