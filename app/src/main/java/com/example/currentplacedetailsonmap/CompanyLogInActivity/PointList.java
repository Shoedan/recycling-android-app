package com.example.currentplacedetailsonmap.CompanyLogInActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currentplacedetailsonmap.DatabaseSQLITE;
import com.example.currentplacedetailsonmap.Markers;
import com.example.currentplacedetailsonmap.R;

import java.util.ArrayList;
import java.util.List;

public class PointList extends AppCompatActivity {

    List<Markers> point_list = new ArrayList<>();
    TextView label;
    int city_id = -1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_list);
        label = findViewById(R.id.textView41);
        label.setVisibility(View.INVISIBLE);
        set_spinner();
        setTitle("City Collection Point List");

    }

    void set_spinner(){
        List<String> spinnerArray;
        DatabaseSQLITE db = new DatabaseSQLITE(this);
        spinnerArray = db.get_city_names();
        spinnerArray.add("No Selection");
        int spinner_size = spinnerArray.size();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray.toArray(new String[0])); // ???
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = findViewById(R.id.spinner3);
        sItems.setAdapter(adapter);
        sItems.setSelection(spinnerArray.size()-1);
        if (getIntent().hasExtra("city")){
            sItems.setSelection(getIntent().getExtras().getInt("city"));
        }
        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (position != spinner_size-1) {
                        point_list = db.get_marker_from_city(position+1);
                        String _aux = "Points from + " + parentView.getSelectedItem();
                        label.setText(_aux);
                        set_listview();
                        city_id = position;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do Nothing
            }

        });
    }

    void set_listview(){
        ArrayList<ListItem> itemlist = new ArrayList<>();
        for (int i=0;i<point_list.size();i++) {
            String point_name = point_list.get(i).getPoint_name();
            boolean _ispaper = point_list.get(i).isPaper();
            boolean _isplastic = point_list.get(i).isPlastic();
            boolean _isglass = point_list.get(i).isGlass();
            int capacity = point_list.get(i).get_max_capacity();
            int current = point_list.get(i).getTotal();
            int id = point_list.get(i).getId();
            itemlist.add(new ListItem(id , point_name , _ispaper,_isplastic,_isglass,capacity , current));
        }
        ListView listView = findViewById(R.id.pointviewer);
        ListViewerAdapter adapter = new ListViewerAdapter(this,itemlist);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Intent myIntent = new Intent(PointList.this,StatsAndDelete.class);
            myIntent.putExtra("id",point_list.get(position).getId());
            myIntent.putExtra("company",getIntent().getStringExtra("company"));
            myIntent.putExtra("actid",2);
            myIntent.putExtra("city",city_id);
            startActivity(myIntent);
            finish();
        });
    }
}