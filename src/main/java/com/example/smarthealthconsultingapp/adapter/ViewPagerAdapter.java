package com.example.smarthealthconsultingapp.adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.smarthealthconsultingapp.fragments.SearchDoctorFragment;
import com.example.smarthealthconsultingapp.fragments.patients.PatientHomeFragment;
import com.example.smarthealthconsultingapp.fragments.patients.PatientProfileFragment;
import com.example.smarthealthconsultingapp.utils.Constants;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    String key;

    public ViewPagerAdapter(@NonNull FragmentManager fm, @NonNull String key) {
        super(fm);
        this.key = key;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new PatientHomeFragment();
            case 1:
                return new SearchDoctorFragment();
            default:
                return new PatientProfileFragment();
        }
    }

    @Override
    public int getCount() {
        int size = 3;
        return size;
    }
}
