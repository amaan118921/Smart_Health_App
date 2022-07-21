package com.example.smarthealthconsultingapp.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.smarthealthconsultingapp.R;
import com.example.smarthealthconsultingapp.activity.MainActivity;
import com.example.smarthealthconsultingapp.utils.Constants;

public class SplashFragment extends BaseFragment {
    @Override
    public int getLayoutRes() {
        return R.layout.fragment_splash;
    }

    private String key = null;

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        assert getArguments() != null;
        key = getArguments().getString(Constants.AUTHENTICATE);
        new Handler(Looper.getMainLooper()).postDelayed(initRunnable(), 1800);
    }

    private Runnable initRunnable() {
        return new Runnable() {
            @Override
            public void run() {
                if(key.equals(Constants.PATIENT_HOME)) {
                    removeFragment(Constants.SPLASH_ID);
                    initViewPager();
                }else {
                    replaceFragment(Constants.DOC_HOME);
                }
            }
        };
    }
}
