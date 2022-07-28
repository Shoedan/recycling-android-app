package com.example.currentplacedetailsonmap.StartingFrame;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.currentplacedetailsonmap.CompanyLogInActivity.CompanyActivity;
import com.example.currentplacedetailsonmap.DatabaseSQLITE;
import com.example.currentplacedetailsonmap.MapsActivity.MapsActivity;
import com.example.currentplacedetailsonmap.R;
import com.example.currentplacedetailsonmap.databinding.FragmentFirstBinding;

public class ChooseUsageStartingActivityFragment extends Fragment {

    private FragmentFirstBinding binding;

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        DatabaseSQLITE database = new DatabaseSQLITE(getActivity());                                // This is used to initialize a database on new devices even if we don't use it in this particular activity.
        binding = FragmentFirstBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }


    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.button.setOnClickListener(view1 -> {
            if (view1.getId() == R.id.button) {
                Intent myIntent = new Intent(view1.getContext(), MapsActivity.class);
                startActivity(myIntent);
                //finish();
            }
        });

        binding.button2.setOnClickListener(view1 -> {
            if (view1.getId() == R.id.button2) {
                Intent myIntent = new Intent(view1.getContext(), CompanyActivity.class);
                startActivity(myIntent);
                //finish();
            }
        });

        binding.button9.setOnClickListener(view1 ->{
            if (view1.getId() == R.id.button9){
                Intent myIntent = new Intent(view1.getContext(), why.class);
                startActivity(myIntent);
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }


}