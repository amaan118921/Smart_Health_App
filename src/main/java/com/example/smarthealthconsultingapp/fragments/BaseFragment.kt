package com.example.smarthealthconsultingapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.smarthealthconsultingapp.MainActivity
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.utils.Constants
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseFragment: Fragment() {
    abstract fun getLayoutRes(): Int

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(getLayoutRes(), container, false)
    }


    fun addFragment(id: String, bundle: Bundle?) {
        getReqFragmentManager().commit {
            setReorderingAllowed(true)
            add(R.id.fragment_container_view, Constants.getFragmentClass(id), bundle, id)
            addToBackStack(id)
        }
    }

    fun replaceFragment(id: String) {
        getReqFragmentManager().commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, Constants.getFragmentClass(id), null)
        }
    }

    fun popBackStack() {
        getReqFragmentManager().popBackStack()
    }

    fun showToast(msg: String) {
        Toast.makeText(requireContext(),msg, Toast.LENGTH_SHORT).show()
    }

    private fun getReqFragmentManager(): FragmentManager = (requireActivity() as MainActivity).getReqFragmentManager()
}