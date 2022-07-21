package com.example.smarthealthconsultingapp.fragments.doc

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.fragments.BaseFragment

class DocHomeFragment : BaseFragment() {

    override fun getLayoutRes(): Int {
        return R.layout.fragment_doc_home
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            finishActivity()
        }
        makeBellVisible()
    }

}