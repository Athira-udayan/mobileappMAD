package com.example.sqlitedb;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    DatabaseHelper myDb;
    EditText editEmployee, editCompany, editDesignation, editPhone;
    Button buttonInsert, buttonView;
    private SQLiteDatabase sql;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        editEmployee = findViewById(R.id.edtName);
        editCompany = findViewById(R.id.edtcompany);
        editDesignation= findViewById(R.id.edtDesignation);
        editPhone = findViewById(R.id.edtPhone);

        buttonInsert = findViewById(R.id.btInsert);
        buttonView = findViewById(R.id.btView);

        AddData();

    }
    public void AddData()
    {
        buttonInsert.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                String name = editEmployee.getText().toString();
                String company = editCompany.getText().toString();
                String designation = editDesignation.getText().toString();
                String phone = editPhone.getText().toString();

                if (TextUtils.isEmpty(name))
                {
                    Toast.makeText(MainActivity.this, "Please enter Phone number",Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(company))
                {
                    Toast.makeText(MainActivity.this, "Please Enter Company Name", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(designation))
                {
                    Toast.makeText(MainActivity.this, "Please enter Designation", Toast.LENGTH_LONG).show();
                    return;
                }
                if (TextUtils.isEmpty(phone))
                {
                    Toast.makeText(MainActivity.this, "Please enter Phone Number", Toast.LENGTH_LONG).show();
                    return;
                }

              boolean isInserted = myDb.insertData(name,company, designation, phone);

                if (isInserted == true)
                {
                    Toast.makeText(MainActivity.this,"Data inserted", Toast.LENGTH_LONG).show();
                    editEmployee.setText("");
                    editCompany.setText("");
                    editDesignation.setText("");
                    editPhone.setText("");
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Data not Inserted", Toast.LENGTH_LONG).show();
                }
            }
        });

        viewAll();
    }

    public void showMessage(String title, String Message)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

    public void viewAll()
    {
       buttonView.setOnClickListener(new View.OnClickListener()
       {
           @Override
           public void onClick(View v)
           {
               Cursor res = myDb.getAllData();
               if (res.getCount() == 0)
               {
                   showMessage("Alert", "Nothing found");
                   return;
               }
               StringBuffer buffer = new StringBuffer();
               while (res.moveToNext())
               {
                   buffer.append("Id:" + res.getString(0) + "\n");

                   buffer.append("Name:-" + res.getString(1) + "\n");

                   buffer.append("Company Name:-" + res.getString(2) + "\n");

                   buffer.append("Designation:-" + res.getString(3) + "\n");

                   buffer.append("Phone:-" + res.getString(4) + "\n\n");

               }

               showMessage("Data", buffer.toString());
           }
       });
    }
}