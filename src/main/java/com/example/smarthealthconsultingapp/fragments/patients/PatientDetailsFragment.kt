package com.example.smarthealthconsultingapp.fragments.patients

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.eventbus.MessageEvent
import com.example.smarthealthconsultingapp.eventbus.StringEvent
import com.example.smarthealthconsultingapp.fragments.BaseFragment
import com.example.smarthealthconsultingapp.model.PatientModel
import com.example.smarthealthconsultingapp.utils.Constants
import com.example.smarthealthconsultingapp.utils.makeGone
import com.example.smarthealthconsultingapp.utils.makeVisible
import com.example.smarthealthconsultingapp.viewModel.AppViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_patient_details.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject


@AndroidEntryPoint
class PatientDetailsFragment: BaseFragment(), View.OnClickListener {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_patient_details
    }

    @Inject
    lateinit var auth: FirebaseAuth

    private val viewModel: AppViewModel by activityViewModels()
    private var img: String? = null
    private var uri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!EventBus.getDefault().isRegistered(this)) EventBus.getDefault().register(this)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {}
        etPhone.setText(repo.getSharedPreferences(Constants.PHONE))
        ivProfile.setOnClickListener(this)
        btnSave.setOnClickListener(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent?) {
        when(event?.getString()) {
            Constants.DONE -> {
                hideProgressIndicator()
            }
            Constants.DATA -> getUri(event)
        }
    }

    private fun getUri(event: MessageEvent) {
        img = (event as StringEvent).getDataString()
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.ivProfile -> openGallery()
            R.id.btnSave -> validate()
        }
    }

    private val launcher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    uri = data.data
                    ivProfile.setImageURI(data.data)
                    showProgressIndicator()
                    viewModel.updateImage(data.data!!)
                }
            }
        }


    private fun showProgressIndicator() {
        tvUploadImage.makeGone()
        piPatientDetails.makeVisible()
    }

    private fun hideProgressIndicator() {
        piPatientDetails.makeGone()
        ivDone.makeVisible()
    }

    private fun validate() {
        val name = etName.text.toString().trim()
        val phone = etPhone.text.toString().trim()
        if(uri==null) showToast("Upload profile image to continue...")
        else if(name.isEmpty()) showToast("Enter name to continue...")
        else if(img==null) showToast("Please wait, Image is being uploaded...")
        else {
            PatientModel().apply {
                patientName = name
                patientPhone = phone
                patientImageUri = img
                uid = auth.currentUser!!.uid
                viewModel.postPatientData(this)
            }
            repo.setSharedPreferences(Constants.IS_LOGGED_IN, Constants.IS_LOGGED_IN)
            popBackStack()
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        launcher.launch(Intent.createChooser(intent, "Choose Image"))
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}