package com.example.smarthealthconsultingapp.utils

import androidx.fragment.app.Fragment
import com.example.smarthealthconsultingapp.bottomSheet.InspectHealthBottomSheet
import com.example.smarthealthconsultingapp.fragments.*
import com.example.smarthealthconsultingapp.fragments.doc.DocDetailsFragment
import com.example.smarthealthconsultingapp.fragments.doc.DocHomeFragment
import com.example.smarthealthconsultingapp.fragments.patients.PatientHomeFragment
import com.example.smarthealthconsultingapp.fragments.patients.PatientLoginFragment

class Constants {
    companion object {
        const val PATIENT_LOGIN_ID = "PATIENT_LOGIN_ID"
        const val CHOOSE_ACCOUNT_ID = "CHOOSE_ACCOUNT_ID"
        const val OTP_ID = "OTP_ID"
        const val PHONE = "PHONE"
        const val APP_NAME = "com.example.smarthealthconsultingapp"
        const val PATIENT_HOME = "PATIENT_HOME"
        const val DOC_HOME = "DOC_HOME"
        const val DOC_PHONE = "DOC_PHONE"
        const val SPLASH_ID = "SPLASH_ID"
        const val IS_LOGGED_IN = "IS_LOGGED_IN"
        const val AUTHENTICATE = "AUTHENTICATE"
        const val PROFILE_ID = "PROFILE_ID"
        const val INSPECT_ID = "INSPECT_ID"
        const val SEARCH_ID = "SEARCH_ID"
        const val DOC_SEARCH_ID = "DOC_SEARCH_ID"
        const val DOC_MODEL_LIST = "DOC_MODEL_LIST"

        fun getFragmentClass(id: String): Class<Fragment> {
            return when(id) {
                SPLASH_ID -> SplashFragment::class.java as Class<Fragment>
                PATIENT_LOGIN_ID -> PatientLoginFragment::class.java as Class<Fragment>
                CHOOSE_ACCOUNT_ID -> ChooseAccountFragment::class.java as Class<Fragment>
                OTP_ID -> OTPFragment::class.java as Class<Fragment>
                PATIENT_HOME -> PatientHomeFragment::class.java as Class<Fragment>
                DOC_HOME -> DocHomeFragment::class.java as Class<Fragment>
                PROFILE_ID -> DocDetailsFragment::class.java as Class<Fragment>
                INSPECT_ID -> InspectHealthFragment::class.java as Class<Fragment>
                SEARCH_ID -> SearchDoctorFragment::class.java as Class<Fragment>
                DOC_SEARCH_ID -> SearchFragment::class.java as Class<Fragment>
                else -> ChooseAccountFragment::class.java as Class<Fragment>
            }
        }
    }
}