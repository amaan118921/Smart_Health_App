package com.example.smarthealthconsultingapp.fragments

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.smarthealthconsultingapp.activity.MainActivity
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

    fun removeFragment(id: String) {
        getReqFragmentManager().commit {
            getReqFragmentManager().findFragmentByTag(id)
                ?.let { remove(it) }
        }
    }

    fun removeOTP() {
//        getReqFragmentManager().commit {
//            getReqFragmentManager().findFragmentByTag(Constants.OTP_ID)
//                ?.let { remove(it) }
//        }
        getReqFragmentManager().popBackStack()
        getReqFragmentManager().popBackStack()
        getReqFragmentManager().commit {
            getReqFragmentManager().findFragmentByTag(Constants.CHOOSE_ACCOUNT_ID)
                ?.let { remove(it) }
        }
    }

    fun initViewPager() {
        (requireActivity() as MainActivity).initViewPagerWithBottom()
    }

    fun bottomVisible() {
        (requireActivity() as MainActivity).showBottomNav()
    }

    fun bottomGone() {
        (requireActivity() as MainActivity).hideBottomNav()
    }

    private fun getReqFragmentManager(): FragmentManager = (requireActivity() as MainActivity).getReqFragmentManager()
}