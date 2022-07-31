package com.example.smarthealthconsultingapp.fragments.doc

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.eventbus.MessageEvent
import com.example.smarthealthconsultingapp.eventbus.StringEvent
import com.example.smarthealthconsultingapp.fragments.BaseFragment
import com.example.smarthealthconsultingapp.model.DoctorModel
import com.example.smarthealthconsultingapp.utils.Constants
import com.example.smarthealthconsultingapp.utils.Repo
import com.example.smarthealthconsultingapp.utils.makeGone
import com.example.smarthealthconsultingapp.utils.makeVisible
import com.example.smarthealthconsultingapp.viewModel.AppViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_doc_details.*
import kotlinx.android.synthetic.main.fragment_doc_details.btnSave
import kotlinx.android.synthetic.main.fragment_doc_details.etName
import kotlinx.android.synthetic.main.fragment_doc_details.ivProfile
import kotlinx.android.synthetic.main.fragment_patient_details.*
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import javax.inject.Inject
@AndroidEntryPoint
class DocDetailsFragment: BaseFragment(), View.OnClickListener {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_doc_details
    }

    private val viewModel: AppViewModel by activityViewModels()
    private var uri: Uri? = null
    private var img: String? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {}
        btnSave.setOnClickListener(this)
        ivProfile.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btnSave -> saveDetails()
            R.id.ivProfile -> openGallery()
        }
    }

    private fun openGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        launcher.launch(Intent.createChooser(intent, "Choose Image"))
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

    private fun hideProgressIndicator() {
        piDocDetails.makeGone()
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
        piDocDetails.makeVisible()
    }


    private fun saveDetails() {
        val name = etName.text.toString().trim()
        val college = etCollege.text.toString().trim()
        val degree = etDegree.text.toString().trim()
        val speciality = etSpeciality.text.toString().trim()
        if(name.isEmpty() || college.isEmpty() || degree.isEmpty() || speciality.isEmpty()) showToast("Enter all details to continue...")
        else if(uri==null) showToast("Upload image to continue...")
        else if(img==null) showToast("Please wait, Image is being uploaded...")
        else {
            DoctorModel().apply {
                docName = name
                this.status = 1
                this.speciality = speciality
                imageUri = img
                this.college = college
                viewModel.postDocData(this)
            }
            repo.setSharedPreferences(Constants.IS_LOGGED_IN, Constants.IS_LOGGED_IN)
            popBackStack()
        }

    }

}