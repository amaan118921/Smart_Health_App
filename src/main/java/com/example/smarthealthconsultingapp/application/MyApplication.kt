package com.example.smarthealthconsultingapp.application

import android.app.Application
import com.example.smarthealthconsultingapp.utils.Repo
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class MyApplication : Application() {

    @Inject
    lateinit var repo: Repo

    override fun onCreate() {
        super.onCreate()
        repo.initSharedPreferences()
    }
}