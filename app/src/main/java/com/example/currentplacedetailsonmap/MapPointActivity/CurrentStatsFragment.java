package com.example.currentplacedetailsonmap.MapPointActivity;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.currentplacedetailsonmap.DatabaseSQLITE;
import com.example.currentplacedetailsonmap.DepositActivity.DepositWasteActivity;
import com.example.currentplacedetailsonmap.Markers;
import com.example.currentplacedetailsonmap.databinding.FragmentFirst2Binding;



public class CurrentStatsFragment extends Fragment {

    private FragmentFirst2Binding binding;


    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {

        binding = FragmentFirst2Binding.inflate(inflater, container, false);


        set_stats();
        Button Deposit = binding.button4;
        Deposit.setOnClickListener(v -> {
            Intent myIntent = new Intent(v.getContext(), DepositWasteActivity.class);
            int id = requireActivity().getIntent().getExtras().getInt("id");
            myIntent.putExtra("id", id);
            startActivity(myIntent);
            requireActivity().finish();
        });

        getActivity().setTitle("Statistics");
        return binding.getRoot();


    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);



    }

    public void set_stats(){
        DatabaseSQLITE dbhelper = new DatabaseSQLITE(getActivity());
        Markers aux = dbhelper.get_Marker(requireActivity().getIntent().getExtras().getInt("id"));
        double lat = aux.getLat();
        double lng = aux.getLon();
        binding.textView16.setText(String.valueOf(lat));
        binding.textView18.setText(String.valueOf(lng));
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
        binding.textView19.setText(available_stuff);
        int current_amount = aux.getTotal();
        binding.textView20.setText(String.valueOf(current_amount));
        int total_capacity = aux.getPc() + aux.getPlc() + aux.getPg();
        binding.textView21.setText(String.valueOf(total_capacity));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }



}