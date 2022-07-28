package com.example.currentplacedetailsonmap.MapPointActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.currentplacedetailsonmap.DatabaseSQLITE;
import com.example.currentplacedetailsonmap.databinding.FragmentLastYearBinding;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;


public class LastYearFragment extends Fragment {

    private FragmentLastYearBinding binding;
    ArrayList<BarEntry> barArrayList;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentLastYearBinding.inflate(inflater, container, false);

        BarChart barChart = binding.barchart;
        set_stats();
        BarDataSet barDataSet = new BarDataSet(barArrayList,"Last Year Statistics");
        BarData barData = new BarData(barDataSet);
        barChart.setData(barData);
        barDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);
        barChart.getDescription().setEnabled(true);

        getActivity().setTitle("Statistics");
        return binding.getRoot();

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    public void set_stats(){
        DatabaseSQLITE db = new DatabaseSQLITE(getActivity());
        List<Integer> statlist = db.get_lys(requireActivity().getIntent().getExtras().getInt("id"));
        binding.textView26.setText(String.valueOf(statlist.get(0)));
        binding.textView27.setText(String.valueOf(statlist.get(1)));
        binding.textView28.setText(String.valueOf(statlist.get(2)));
        binding.textView29.setText(String.valueOf(statlist.get(3)));
        barArrayList = new ArrayList<>();
        barArrayList.add(new BarEntry(1f,statlist.get(0)));
        barArrayList.add(new BarEntry(2f,statlist.get(1)));
        barArrayList.add(new BarEntry(3f,statlist.get(2)));
        barArrayList.add(new BarEntry(4f,statlist.get(3)));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}