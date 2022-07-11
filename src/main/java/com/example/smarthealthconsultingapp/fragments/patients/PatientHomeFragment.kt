package com.example.smarthealthconsultingapp.fragments.patients

import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.fragments.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PatientHomeFragment: BaseFragment() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_patient_home
    }
}