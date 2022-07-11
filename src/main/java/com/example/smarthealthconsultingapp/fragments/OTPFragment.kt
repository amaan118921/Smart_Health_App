package com.example.smarthealthconsultingapp.fragments

import `in`.aabhasjindal.otptextview.OTPListener
import android.app.ProgressDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.utils.Constants
import com.example.smarthealthconsultingapp.utils.Repo
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_otp.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@AndroidEntryPoint
class OTPFragment: BaseFragment(), OTPListener, View.OnClickListener {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_otp
    }

    private var phone: String? = null
    private var mobile: String? = null
    private lateinit var userid: String
    private lateinit var userToken: PhoneAuthProvider.ForceResendingToken
    private lateinit var dialog: ProgressDialog

    @Inject
    lateinit var auth: FirebaseAuth

    @Inject
    lateinit var repo: Repo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mobile = arguments?.getString(Constants.PHONE)
        phone = "+91$mobile"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        otp_view.otpListener = this
        getCallbacks()
        btnBack.setOnClickListener(this)
        btnVerify.setOnClickListener(this)
        tvNum.text = "Enter the OTP sent on $mobile"
    }

    override fun onStart() {
        super.onStart()
        otp_view.requestFocusOTP()
    }


    private fun getCallbacks() {

        val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                showToast("Verification Successful")
                signInWithPhoneAuthCredential(p0)
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                if (p0 is FirebaseAuthInvalidCredentialsException) {
                    showToast("Invalid Request")
                } else {
                    showToast(p0.message.toString())
                }
               popBackStack()
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                userid = p0
                userToken = p1
            }

        }

        val options = phone?.let {
            PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(it).setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(requireActivity())
                .setCallbacks(callbacks)
                .build()
        }
        options?.let { PhoneAuthProvider.verifyPhoneNumber(it)}
    }


    private fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential).addOnCompleteListener { p0 ->
            if (p0.isSuccessful) {
                dialog.dismiss()
                val newUser = p0.result!!.additionalUserInfo!!.isNewUser
                if (newUser) {
                    showToast("Verification Successful")
                } else {
                    showToast("Welcome Back")
                }
                repo.setSharedPreferences(Constants.PHONE, Constants.PHONE)
                replaceFragment(Constants.PATIENT_HOME)
            } else {
                dialog.dismiss()
                showToast("Incorrect OTP")
                popBackStack()
            }
        }
    }

    override fun onInteractionListener() {}

    override fun onOTPComplete(otp: String) = verifyOTP(otp)

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btnVerify -> {
                val otp = otp_view.otp
                if(otp?.length!! ==6) verifyOTP(otp)
                else showToast("Enter 6 digit OTP")
            }
            R.id.btnBack -> popBackStack()
        }
    }

    private fun verifyOTP(otp: String?) {
        callDialog()
        val credential = otp?.let { PhoneAuthProvider.getCredential(userid, it) }
        credential?.let { signInWithPhoneAuthCredential(it) }
    }

    private fun callDialog() {
        dialog = ProgressDialog(requireContext())
        dialog.setMessage("Please Wait")
        dialog.setCancelable(false)
        dialog.show()
    }

}