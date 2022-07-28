package com.example.currentplacedetailsonmap.DepositActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.currentplacedetailsonmap.DatabaseSQLITE;
import com.example.currentplacedetailsonmap.MapPointActivity.StatsTabActivity;
import com.example.currentplacedetailsonmap.Markers;
import com.example.currentplacedetailsonmap.R;

import java.util.ArrayList;
import java.util.List;

public class DepositWasteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_deposit_waste);
        set_capacity();
    }

    public void set_capacity(){
        DatabaseSQLITE dbhelper = new DatabaseSQLITE(DepositWasteActivity.this);
        // Set Text with Capacity
        TextView Paper = findViewById(R.id.textView34);
        TextView Plastic = findViewById(R.id.textView35);
        TextView Glass = findViewById(R.id.textView36);
        int id = getIntent().getExtras().getInt("id");
        Markers _aux = dbhelper.get_Marker(id);
        String _paper = _aux.getCp() + " / " + _aux.getPc();
        String _plastic = _aux.getCpl() + " / " + _aux.getPlc();
        String _glass = _aux.getCg() + " / " + _aux.getPg();
        Paper.setText(_paper);
        Plastic.setText(_plastic);
        Glass.setText(_glass);

        // EditBox Hidden at start
        EditText editbox = findViewById(R.id.plain_text_input);
        editbox.setVisibility(View.INVISIBLE);
        Button Done = findViewById(R.id.button6);
        Done.setVisibility(View.INVISIBLE);

        // Setup Spinner with Items
        List<String> spinnerArray = new ArrayList<>();
        spinnerArray.add("Paper");
        spinnerArray.add("Plastic");
        spinnerArray.add("Glass");
        spinnerArray.add("No Selection");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner sItems = findViewById(R.id.spinner);
        sItems.setAdapter(adapter);
        sItems.setSelection(3);
        sItems.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                if (sItems.getSelectedItemPosition() != 3) {
                    editbox.setVisibility(View.VISIBLE);
                    Done.setVisibility(View.VISIBLE);
                }
                if(sItems.getSelectedItemPosition() == 3){
                    editbox.setVisibility(View.INVISIBLE);
                    Done.setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Do Stuff
            }

        });

        // Button and EditBox Interaction
        CharSequence seq = "The amount you want to recycle is bigger than the space available";
        Toast toast = Toast.makeText(DepositWasteActivity.this, seq, Toast.LENGTH_SHORT);
        Done.setOnClickListener(v -> {
            int error_check = Integer.parseInt(editbox.getText().toString());
            switch (sItems.getSelectedItemPosition()) {
                case 0:
                    if (error_check + _aux.getCp() > _aux.getPc())
                        toast.show();
                    else{
                        dbhelper.update_paper(_aux,error_check);
                        finish();
                        startActivity(getIntent());}
                    break;
                case 1:
                    if (error_check + _aux.getCpl() > _aux.getPlc())
                        toast.show();
                    else{
                        dbhelper.update_plastic(_aux,error_check);
                        finish();
                        startActivity(getIntent());}
                    break;
                case 2:
                    if (error_check + _aux.getCg() > _aux.getPg())
                        toast.show();
                    else{
                        dbhelper.update_glass(_aux,error_check);
                        finish();
                        startActivity(getIntent());}
                    break;
            }
        });

    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, StatsTabActivity.class);
        intent.putExtra("id",getIntent().getExtras().getInt("id"));
        startActivity(intent);
        finish();
    }
}