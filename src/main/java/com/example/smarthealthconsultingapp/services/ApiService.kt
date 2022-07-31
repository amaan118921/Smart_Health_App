package com.example.smarthealthconsultingapp.services

import com.example.smarthealthconsultingapp.model.Doctor
import com.example.smarthealthconsultingapp.model.DoctorModel
import com.example.smarthealthconsultingapp.model.Patient
import com.example.smarthealthconsultingapp.model.PatientModel
import com.example.smarthealthconsultingapp.utils.Constants
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.*

private val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
private val retrofit = Retrofit.Builder().addConverterFactory(MoshiConverterFactory.create(moshi)).baseUrl(Constants.BASE_URL).build()

interface ApiService {

    @POST("patient/{uid}")
    suspend fun postPatientData(@Body patient: Patient, @Path("uid") uid: String)


    @POST("doctor/{uid}")
    suspend fun postDoctorData(@Body doctor: Doctor, @Path("uid") uid: String)


    @GET("patientData/{uid}")
    suspend fun getPatientData(@Path("uid") uid: String): PatientModel

    @GET("doctor/all")
    suspend fun getAllDoctors(): List<DoctorModel>
}


object API {
    val retrofitService: ApiService by lazy { retrofit.create(ApiService::class.java) }
}