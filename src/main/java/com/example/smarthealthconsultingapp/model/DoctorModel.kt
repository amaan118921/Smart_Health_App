package com.example.smarthealthconsultingapp.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class DoctorModel(
    var docName: String? = null, var speciality: String? = null, var status: String? = null, var imageUri: String? = null
): Parcelable