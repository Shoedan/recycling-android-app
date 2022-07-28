package com.example.currentplacedetailsonmap.CompanyLogInActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currentplacedetailsonmap.CompanyMenu.MenuActivity;
import com.example.currentplacedetailsonmap.DatabaseSQLITE;
import com.example.currentplacedetailsonmap.R;

public class CompanyActivity extends AppCompatActivity {

    DatabaseSQLITE dbhelper ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company);
        setTitle("Company LogIn");
        EditText pass = findViewById(R.id.editTextTextPersonName);
        TextView title = findViewById(R.id.textView38);
        get_db();
        Button login = findViewById(R.id.button7);
        login.setOnClickListener(v -> {
            String password = pass.getText().toString();
            String account = dbhelper.get_account_passwords(password);
            if (!account.equals("")){
                Intent myIntent = new Intent(this, MenuActivity.class);
                myIntent.putExtra("company" , account);
                startActivity(myIntent);
            }
            else{
                String st = "Wrong password";
                title.setText(st);
            }

        });

    }

    private void get_db(){
        dbhelper = new DatabaseSQLITE(this);
    }



}