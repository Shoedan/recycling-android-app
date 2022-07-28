package com.example.currentplacedetailsonmap.CompanyMenu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currentplacedetailsonmap.CompanyLogInActivity.CleaningRouteAct;
import com.example.currentplacedetailsonmap.CompanyLogInActivity.ComboBoxMapAct;
import com.example.currentplacedetailsonmap.CompanyLogInActivity.MonitoringMode;
import com.example.currentplacedetailsonmap.CompanyLogInActivity.MonthSwap;
import com.example.currentplacedetailsonmap.CompanyLogInActivity.PointList;
import com.example.currentplacedetailsonmap.CompanyLogInActivity.YearSwap;
import com.example.currentplacedetailsonmap.ListViewerActivities.CompanyMap;
import com.example.currentplacedetailsonmap.R;

public class MenuActivity extends AppCompatActivity {

    ListView listView;
    TextView textView;

    String[] mobileTypes = {
            "Show Map at current Location" , "Check a specific City" , "Get Point List" , "Get Route for city" , "New Month Switch" , "New Year Switch" , "Enter Monitoring Mode"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_successful_log_in);
        textView = findViewById(R.id.textView39);
        String company_name = "You are logged in on a " + getIntent().getStringExtra("company") + " account";
        textView.setText(company_name);
        listView = findViewById(R.id.listviewerx);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mobileTypes);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            switch (position){
                case 0 :    Intent myIntent = new Intent(view.getContext(), CompanyMap.class);            // Create its own Maps Class;
                            myIntent.putExtra("company",getIntent().getStringExtra("company"));
                            startActivity(myIntent);
                            break;
                case 1 :    Intent myIntent2 = new Intent(view.getContext(), ComboBoxMapAct.class);
                            myIntent2.putExtra("company",getIntent().getStringExtra("company"));
                            startActivity(myIntent2);
                            break;

                case 2 :    Intent myIntent3 = new Intent(view.getContext() , PointList.class);
                            myIntent3.putExtra("company", getIntent().getStringExtra("company"));
                            startActivity(myIntent3);
                            break;
                case 3 :    Intent myIntent4 = new Intent(view.getContext(), CleaningRouteAct.class);
                            myIntent4.putExtra("company" , getIntent().getStringExtra("company"));
                            startActivity(myIntent4);
                            break;
                case 4 :    Intent myIntent5 = new Intent(view.getContext(), MonthSwap.class);
                            myIntent5.putExtra("company",getIntent().getStringExtra("company"));
                            startActivity(myIntent5);
                            break;
                case 5 :    Intent myIntent6 = new Intent(view.getContext(), YearSwap.class);
                            myIntent6.putExtra("company", getIntent().getStringExtra("company"));
                            startActivity(myIntent6);
                            break;
                case 6 :    Intent myIntent7 = new Intent(view.getContext(), MonitoringMode.class);
                            myIntent7.putExtra("company", getIntent().getStringExtra("company"));
                            startActivity(myIntent7);
                            break;

            }
        });

    }
}