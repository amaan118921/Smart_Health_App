package com.example.smarthealthconsultingapp.fragments.patients

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.addCallback
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.fragments.BaseFragment
import com.example.smarthealthconsultingapp.utils.Constants
import com.example.smarthealthconsultingapp.utils.Repo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class PatientHomeFragment: BaseFragment() {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_patient_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            finishActivity()
        }
        checkForProfile()
        bottomVisible()
    }

    private fun checkForProfile() {
        if(repo.getSharedPreferences(Constants.IS_LOGGED_IN)=="") showProfileDialog()
    }

    private fun showProfileDialog() {
        addFragment(Constants.PATIENT_DETAILS, null, true)
    }
}