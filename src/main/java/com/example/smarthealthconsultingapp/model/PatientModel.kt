package com.example.smarthealthconsultingapp.model

data class PatientModel(
    var patientName: String? = null, var patientPhone: String? = null, var patientImageUri: String? = null, var uid: String? = null
)


data class Patient(
    var patient: PatientModel? = null
)