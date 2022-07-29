package com.example.smarthealthconsultingapp.fragments.patients;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarthealthconsultingapp.R;
import com.example.smarthealthconsultingapp.fragments.BaseFragment;

public class PatientProfileFragment extends BaseFragment {
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_patient_profile;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
