package com.example.smarthealthconsultingapp.fragments.doc

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.fragments.BaseFragment
import com.example.smarthealthconsultingapp.utils.Constants
import com.example.smarthealthconsultingapp.utils.Repo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_doc_details.*
import javax.inject.Inject
@AndroidEntryPoint
class DocDetailsFragment: BaseFragment(), View.OnClickListener {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_doc_details
    }

    @Inject
    lateinit var repo: Repo

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {}
        btnSave.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btnSave -> saveDetails()
        }
    }

    private fun saveDetails() {
        repo.setSharedPreferences(Constants.IS_LOGGED_IN, Constants.IS_LOGGED_IN)
        popBackStack()
    }

}