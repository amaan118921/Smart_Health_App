package com.example.smarthealthconsultingapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.smarthealthconsultingapp.utils.Constants
import com.example.smarthealthconsultingapp.utils.Repo
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var repo: Repo

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        authenticate()
    }

    private fun authenticate() {
        if(repo.getSharedPreferences(Constants.PHONE)!="") addFragment(Constants.PATIENT_HOME)
        else if(repo.getSharedPreferences(Constants.DOC_PHONE)!="") addFragment(Constants.DOC_HOME)
        else addFragment(Constants.CHOOSE_ACCOUNT_ID)
    }

    fun getReqFragmentManager(): FragmentManager = supportFragmentManager

    private fun addFragment(id: String) {
        getReqFragmentManager().commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view, Constants.getFragmentClass(id), null, id)
        }
    }

}