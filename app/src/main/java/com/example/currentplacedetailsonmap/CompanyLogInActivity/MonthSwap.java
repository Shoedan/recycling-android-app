package com.example.currentplacedetailsonmap.CompanyLogInActivity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currentplacedetailsonmap.DatabaseSQLITE;
import com.example.currentplacedetailsonmap.R;

public class MonthSwap extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Month Rollover");
        setContentView(R.layout.activity_month_swap);
        Button button = findViewById(R.id.wrongbutton8);
        button.setOnClickListener(v -> {
            DatabaseSQLITE db = new DatabaseSQLITE(MonthSwap.this);
            db.roll_over_month();
        });
    }
}