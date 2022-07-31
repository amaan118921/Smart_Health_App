package com.example.smarthealthconsultingapp.fragments.doc

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.fragments.BaseFragment
import com.example.smarthealthconsultingapp.utils.Constants
import com.example.smarthealthconsultingapp.utils.Repo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.action_bar.*
import javax.inject.Inject
@AndroidEntryPoint
class DocHomeFragment : BaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_doc_home
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            finishActivity()
        }
        tvHello.text = "Welcome"
        makeBellVisible()
        checkForProfile()
    }

    private fun checkForProfile() {
        if(repo.getSharedPreferences(Constants.IS_LOGGED_IN)=="") showProfileDialog()
    }

    private fun showProfileDialog() {
        addFragment(Constants.PROFILE_ID, null, true)
    }

}