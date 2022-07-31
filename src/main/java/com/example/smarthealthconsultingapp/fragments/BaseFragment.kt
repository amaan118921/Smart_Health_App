package com.example.smarthealthconsultingapp.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.activity.MainActivity
import com.example.smarthealthconsultingapp.utils.Constants
import com.example.smarthealthconsultingapp.utils.Repo
import com.example.smarthealthconsultingapp.utils.makeVisible
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.action_bar.*
import kotlinx.android.synthetic.main.fragment_search.*
import javax.inject.Inject


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

    @Inject
    lateinit var repo: Repo

    fun addFragment(id: String, bundle: Bundle?, animations: Boolean) {
        getReqFragmentManager().commit {
            setReorderingAllowed(true)
            if(animations) setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            add(R.id.fragment_container_view, Constants.getFragmentClass(id), bundle, id)
            addToBackStack(id)
        }
    }

    fun replaceFragment(id: String) {
        getReqFragmentManager().commit {
            setReorderingAllowed(true)
            setCustomAnimations(R.anim.slide_in, R.anim.fade_out, R.anim.fade_in, R.anim.slide_out)
            replace(R.id.fragment_container_view, Constants.getFragmentClass(id), null)
        }
    }

    fun finishActivity() {
        requireActivity().finish()
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


    fun initViewPager() {
        (requireActivity() as MainActivity).initViewPagerWithBottom()
    }

    fun bottomVisible() {
        (requireActivity() as MainActivity).showBottomNav()
    }

    fun bottomGone() {
        (requireActivity() as MainActivity).hideBottomNav()
    }

    fun finishAndStart() {
        (requireActivity() as MainActivity).finish()
        startActivity(Intent(requireContext(), MainActivity::class.java))
    }

    fun makeBellVisible() {
        ivBell.makeVisible()
    }

    fun showSoftKeyboard() {
        val imm: InputMethodManager? =
            getSystemService(requireContext(), InputMethodManager::class.java)
        imm?.showSoftInput(etSearch, InputMethodManager.SHOW_IMPLICIT)
    }

    fun hideKeyboard() {
        val imm: InputMethodManager? =
            getSystemService(requireContext(), InputMethodManager::class.java)
        imm?.hideSoftInputFromWindow(etSearch.windowToken, 0)
    }

    fun clearSharedPref() {
        repo.clearSharedPreferences(Constants.PHONE)
        repo.clearSharedPreferences(Constants.DOC_PHONE)
        repo.clearSharedPreferences(Constants.IS_LOGGED_IN)
    }
    private fun getReqFragmentManager(): FragmentManager = (requireActivity() as MainActivity).getReqFragmentManager()
}