package com.example.currentplacedetailsonmap.CompanyLogInActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currentplacedetailsonmap.DatabaseSQLITE;
import com.example.currentplacedetailsonmap.R;

import java.util.List;

public class MonitoringMode extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitoring_mode);
        set_spinner();
    }


    public void set_spinner(){
        List<String> spinnerArray;
        DatabaseSQLITE db = new DatabaseSQLITE(this);
        spinnerArray = db.get_city_names();
        spinnerArray.add("No Selection");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = findViewById(R.id.spinnermonitor);
        sItems.setAdapter(adapter);
        sItems.setSelection(spinnerArray.size()-1);
        Button start = findViewById(R.id.button8);
        Button stop = findViewById(R.id.button11);
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sItems.getSelectedItemPosition() != spinnerArray.size()-1){
                    Intent myIntent = new Intent(MonitoringMode.this, MonitoringService.class);
                    myIntent.putExtra("city_name" , sItems.getSelectedItem().toString());
                    myIntent.putExtra("pos",sItems.getSelectedItemPosition()+1);
                    startService(myIntent);
                }
            }
        });
        stop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myIntent = new Intent(MonitoringMode.this, MonitoringService.class);
                stopService(myIntent);
            }
        });
    }

}