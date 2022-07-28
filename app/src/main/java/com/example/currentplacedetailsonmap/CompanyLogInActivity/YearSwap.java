package com.example.currentplacedetailsonmap.CompanyLogInActivity;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currentplacedetailsonmap.DatabaseSQLITE;
import com.example.currentplacedetailsonmap.R;

public class YearSwap extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_year_swap);
        setTitle("Year Rollover");
        Button button = findViewById(R.id.button10);
        button.setOnClickListener(v -> {
            DatabaseSQLITE db = new DatabaseSQLITE(YearSwap.this);
            db.roll_over_year();
        });
    }
}