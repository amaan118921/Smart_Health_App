package com.example.smarthealthconsultingapp.fragments

import android.os.Bundle
import android.view.View
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_choose_account.*
@AndroidEntryPoint
class ChooseAccountFragment: BaseFragment(), View.OnClickListener {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_choose_account
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cvPatient.setOnClickListener(this)
        cvDoctors.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.cvPatient -> {
                Bundle().apply {
                    putString(Constants.AUTHENTICATE, Constants.PATIENT_HOME)
                    addFragment(Constants.PATIENT_LOGIN_ID,this, true)
                }
            }
            R.id.cvDoctors -> {
                Bundle().apply {
                    putString(Constants.AUTHENTICATE, Constants.DOC_HOME)
                    addFragment(Constants.PATIENT_LOGIN_ID,this, true)
                }
            }
        }
    }

}