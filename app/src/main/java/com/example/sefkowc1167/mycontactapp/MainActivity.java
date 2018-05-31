package com.example.sefkowc1167.mycontactapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    public static DatabaseHelper myDb;
    public EditText editName;
    public EditText editAddress;
    public EditText editPhone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editName = findViewById(R.id.editText_name);
        editAddress = findViewById(R.id.editText_address);
        editPhone = findViewById(R.id.editText_phone);

        myDb = new DatabaseHelper(this);
        Log.d("MyContactApp", "MainActivity: instantiated. DBHelper");
    }

    public void addData(View view) {
        Log.d("MyContactApp", "MainActivity: Add contact button pressed");
        Log.d("MyContactApp", editName.getText().toString());
        Log.d("MyContactApp", editAddress.getText().toString());
        Log.d("MyContactApp", editPhone.getText().toString());
        boolean isInserted = myDb.insertData(editName.getText().toString(), editAddress.getText().toString(), editPhone.getText().toString());

        if (isInserted) {
            Toast.makeText(MainActivity.this, "Success, contact inserted", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(MainActivity.this, "Failed, contact not inserted", Toast.LENGTH_LONG).show();
        }
    }

    public void viewData (View view) {
        Cursor res = myDb.getAllData();
        Log.d("MyContactApp", "MainActivity: viewData: received cursor " + res.getCount());
        if (res.getCount() == 0) {
            showMessage("Error", "no data found in database");
        }

        StringBuffer sb = new StringBuffer();
        while(res.moveToNext()) {
            // append res column 0,1,2,3 to buffer; delimit "appends" w "\n"
            for (int i = 0; i < 4; i++) {
                sb.append(res.getColumnName(i) + ": " + res.getString(i) + "\n");
            }
            sb.append("\n");
        }
        Log.d("MyContactApp", "MainActivity: assembled StringBuffer");
        showMessage("Data", sb.toString());
        Log.d("MyContactApp", "MainActivity: displaying contacts");
    }

    public void showMessage(String title, String message) {
        Log.d("MyContactApp", "MainActivity: showMessage: building alert dialog");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public static final String EXTRA_MESSAGE = "com.example.sefkowc1167.mycontactapp.MESSAGE";
    public void SearchRecord(View view) {
        Log.d("MyContactApp", "MainActivity: entered SearchRecord");
        Intent intent = new Intent(this, SearchActivity.class);
        intent.putExtra(EXTRA_MESSAGE, searchDb());
        startActivity(intent);
    }

    private String searchDb() {
        Cursor res = myDb.getAllData();
        Log.d("MyContactApp", "MainActivity: entered searchDb");
        StringBuffer sb = new StringBuffer();
        int x = 0;

        // iterate through the db
        while (res.moveToNext()) {
            // if the name in the db is the same as the name in the edit field add it to the string
            if (res.getString(1).equals(editName.getText().toString())) {
                for (int i = 1; i < 4; i++) {
                    sb.append(res.getColumnName(i) + ": " + res.getString(i) + "\n");
                }
                sb.append("\n");
                x++;
            }
        }

        // nothing found
        if (x == 0) {
            return "No entries found.";
        } else {
            // otherwise return the info
            String name = editName.getText().toString();
            if (name.equals("")) {
                // Prevent empty string error
                name = " ";
            }
            sb.insert(0, x + " entries found.");
            return sb.toString();
        }
    }

}