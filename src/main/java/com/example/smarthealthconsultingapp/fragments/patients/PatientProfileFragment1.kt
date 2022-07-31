package com.example.smarthealthconsultingapp.fragments.patients


import dagger.hilt.android.AndroidEntryPoint
import com.example.smarthealthconsultingapp.fragments.BaseFragment
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.viewModel.AppViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.textview.MaterialTextView
import de.hdodenhof.circleimageview.CircleImageView
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.activityViewModels
import com.example.smarthealthconsultingapp.bottomSheet.BottomSheetEdit
import com.example.smarthealthconsultingapp.model.PatientModel
import com.example.smarthealthconsultingapp.utils.makeGone
import com.example.smarthealthconsultingapp.utils.makeVisible
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.button.MaterialButton
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_patient_details.*
import kotlinx.android.synthetic.main.fragment_patient_profile.*
import kotlinx.android.synthetic.main.fragment_patient_profile.ivProfile
import java.util.*

@AndroidEntryPoint
class PatientProfileFragment : BaseFragment(), View.OnClickListener {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_patient_profile
    }

    private val viewModel: AppViewModel by activityViewModels()
    private lateinit var bottomSheet: BottomSheetDialog

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btnSignOut.setOnClickListener(this)
        initView()
    }

    private fun initView() {
        viewModel.patientModel.observe(viewLifecycleOwner) {
            tvName.text = it.patientName
            tvPhone.text = it.patientPhone
            try {
                it.patientImageUri?.let{img -> Picasso.get().load(img).into(ivProfile)}
            } catch (e: Exception) {}
            showLayout()
        }
    }

    private fun showLayout() {
        pfPatientProfile.makeGone()
        clPatientProfile.makeVisible()
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btnSignOut -> showConfirmDialog()
        }
    }

    private fun signOut() {
        clearSharedPref()
        dismiss()
        finishAndStart()
    }

    private fun showConfirmDialog() {
        bottomSheet = BottomSheetDialog(requireContext())
        bottomSheet.setContentView(R.layout.fragment_bottom_signout)
        val btnYes = bottomSheet.findViewById<MaterialButton>(R.id.btnYes)
        val btnCancel = bottomSheet.findViewById<ImageView>(R.id.ivCancel)
        btnYes?.setOnClickListener {
            signOut()
        }
        btnCancel?.setOnClickListener {
            dismiss()
        }
        bottomSheet.show()
    }

    private fun dismiss() {
        bottomSheet.dismissWithAnimation = true
        bottomSheet.dismiss()
    }

}