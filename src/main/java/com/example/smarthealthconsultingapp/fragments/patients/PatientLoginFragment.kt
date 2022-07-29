package com.example.smarthealthconsultingapp.fragments.patients

import android.os.Bundle
import android.view.View
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.fragments.BaseFragment
import com.example.smarthealthconsultingapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_patient_login.*
@AndroidEntryPoint
class PatientLoginFragment: BaseFragment(), View.OnClickListener {

    private var key: String? = null

    override fun getLayoutRes(): Int {
        return R.layout.fragment_patient_login
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        key = arguments?.getString(Constants.AUTHENTICATE)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnNext.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btnNext -> validate()
        }
    }

    private fun validate() {
        val phone = etMobile.text.toString().trim()
        if(phone.length<10) showToast("Enter 10 digit mobile number")
        else {
            Bundle().apply {
                putString(Constants.PHONE, phone)
                putString(Constants.AUTHENTICATE, key)
                addFragment(Constants.OTP_ID, this, true)
            }
        }
    }

}