package com.example.smarthealthconsultingapp.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.smarthealthconsultingapp.eventbus.MessageEvent
import com.example.smarthealthconsultingapp.eventbus.StringEvent
import com.example.smarthealthconsultingapp.model.Doctor
import com.example.smarthealthconsultingapp.model.DoctorModel
import com.example.smarthealthconsultingapp.model.Patient
import com.example.smarthealthconsultingapp.model.PatientModel
import com.example.smarthealthconsultingapp.services.API
import com.example.smarthealthconsultingapp.utils.Constants
import com.example.smarthealthconsultingapp.utils.Repo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor() : ViewModel() {

    private var storage: FirebaseStorage = FirebaseStorage.getInstance()
    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private var storageRef: StorageReference? = null
    private var imgUri: String? = null

    private var _patientModel = MutableLiveData<PatientModel>()
    var patientModel : LiveData<PatientModel> = _patientModel

    private var _allDoctors = MutableLiveData<List<DoctorModel>>()
    var allDoctors : LiveData<List<DoctorModel>> = _allDoctors

    init {
        getPatientData()
        getAllDoctors()
    }

    private fun getAllDoctors() {
        viewModelScope.launch {
            _allDoctors.value = API.retrofitService.getAllDoctors()
            allDoctors = _allDoctors
        }
    }

    private fun getPatientData() {
            viewModelScope.launch {
                auth.currentUser?.uid?.let {
                    _patientModel.value = API.retrofitService.getPatientData(it)
                    patientModel = _patientModel
                }
            }
    }

    fun updateImage(uri: Uri) {
        storageRef = auth.currentUser?.uid?.let { storage.reference.child(it) }
        viewModelScope.launch {
            try {
                uri.let { it ->
                    storageRef?.putFile(it)?.addOnCompleteListener { task ->
                        if(task.isSuccessful) {
                            storageRef!!.downloadUrl.addOnSuccessListener {
                                imgUri = it.toString()
                                EventBus.getDefault().post(MessageEvent(Constants.DONE))
                                imgUri?.let { img -> EventBus.getDefault().post(StringEvent(Constants.DATA, img)) }
                            }
                        }
                    }
                }
            } catch (e: Exception) { }
        }
    }


    fun postPatientData(patientModel: PatientModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Patient().apply {
                    patient = patientModel
                    API.retrofitService.postPatientData(this, auth.currentUser!!.uid)
                }

            } catch (e:Exception) {}
        }
    }

    fun postDocData(docModel: DoctorModel) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Doctor().apply {
                    doctor = docModel
                    API.retrofitService.postDoctorData(this, auth.currentUser!!.uid)
                }

            } catch (e:Exception) {}
        }
    }


}