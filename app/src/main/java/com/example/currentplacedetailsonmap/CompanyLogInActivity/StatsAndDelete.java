package com.example.currentplacedetailsonmap.CompanyLogInActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currentplacedetailsonmap.DatabaseSQLITE;
import com.example.currentplacedetailsonmap.ListViewerActivities.CompanyMap;
import com.example.currentplacedetailsonmap.Markers;
import com.example.currentplacedetailsonmap.R;

public class StatsAndDelete extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats_and_delete);
        set_stats();
        setTitle("Collection Point Statistics");
        Button Empty = findViewById(R.id.button4);
        DatabaseSQLITE dbhelper = new DatabaseSQLITE(this);
        Empty.setOnClickListener(v -> {
            dbhelper.empty_point(getIntent().getExtras().getInt("id"));
            finish();
            startActivity(getIntent());
        });
    }

    public void set_stats(){
        DatabaseSQLITE dbhelper = new DatabaseSQLITE(this);
        Markers aux = dbhelper.get_Marker(getIntent().getExtras().getInt("id"));
        double lat = aux.getLat();
        double lng = aux.getLon();
        TextView textlat = findViewById(R.id.textView16);
        TextView textlng = findViewById(R.id.textView18);
        textlat.setText(String.valueOf(lat));
        textlng.setText(String.valueOf(lng));
        boolean ispaper = aux.isPaper();
        boolean isplastic = aux.isPlastic();
        boolean isglass = aux.isGlass();
        String available_stuff="";
        if (ispaper)
            available_stuff += "Paper ";
        if (isplastic)
            available_stuff += "/ Plastic ";
        if (isglass)
            available_stuff += "/ Glass";
        TextView avb = findViewById(R.id.textView19);
        avb.setText(available_stuff);
        int current_amount = aux.getTotal();
        TextView cam = findViewById(R.id.textView20);
        cam.setText(String.valueOf(current_amount));
        int total_capacity = aux.getPc() + aux.getPlc() + aux.getPg();
        TextView total = findViewById(R.id.textView21);
        total.setText(String.valueOf(total_capacity));
    }

    @Override
    public void onBackPressed() {
        switch(getIntent().getExtras().getInt("actid")){
            case 1 : Intent intent = new Intent(this, CompanyMap.class);
                    intent.putExtra("company",getIntent().getStringExtra("company"));
                    startActivity(intent);
                    finish();
                    break;
            case 2 :Intent intent2 = new Intent(this, PointList.class);
                    intent2.putExtra("company",getIntent().getStringExtra("company"));
                    intent2.putExtra("city",getIntent().getExtras().getInt("city"));
                    startActivity(intent2);
                    finish();
                    break;
            case 3 :Intent intent3 = new Intent(this,ComboBoxMapAct.class);
                    intent3.putExtra("company",getIntent().getStringExtra("company"));
                    intent3.putExtra("city",getIntent().getExtras().getInt("city"));
                    startActivity(intent3);
                    finish();
                    break;
            case 4 :Intent intetn4 = new Intent(this,CleaningRouteAct.class);
                    intetn4.putExtra("company",getIntent().getStringExtra("company"));
                    intetn4.putExtra("city",getIntent().getExtras().getInt("ciy"));
                    startActivity(intetn4);
                    finish();
                    break;
        }

    }
}