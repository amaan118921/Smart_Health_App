package com.example.smarthealthconsultingapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.adapter.DoctorAdapter
import com.example.smarthealthconsultingapp.model.DoctorModel
import com.example.smarthealthconsultingapp.utils.Constants
import com.example.smarthealthconsultingapp.utils.getDocList
import com.example.smarthealthconsultingapp.utils.makeGone
import com.example.smarthealthconsultingapp.utils.makeVisible
import com.example.smarthealthconsultingapp.viewModel.AppViewModel
import kotlinx.android.synthetic.main.fragment_search_doctor.*
import java.util.ArrayList

class SearchDoctorFragment: BaseFragment(), View.OnClickListener{
    override fun getLayoutRes(): Int {
        return R.layout.fragment_search_doctor
    }

    private val viewModel: AppViewModel by activityViewModels()
    private var adapter: DoctorAdapter? = null
    private var docList: List<DoctorModel> = listOf()

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            popBackStack()
        }
        viewModel.allDoctors.observe(viewLifecycleOwner) {
            adapter = DoctorAdapter(it, requireContext())
            docList = it
            adapter?.notifyDataSetChanged()
            showLayout()
        }

        setUpRecyclerView()
        cvSearch.setOnClickListener(this)
        ivCancel.setOnClickListener(this)
    }

    private fun showLayout() {
        clDocSearch.makeVisible()
        pfDocSearch.makeGone()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRecyclerView() {
        rvDoctors.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchDoctorFragment.adapter
            adapter?.notifyDataSetChanged()
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.ivCancel -> popBackStack()
            R.id.cvSearch -> searchFragment()
        }
    }

    private fun searchFragment() {
        Bundle().apply {
            putParcelableArrayList(Constants.DOC_MODEL_LIST,docList as ArrayList<out Parcelable>)
            addFragment(Constants.DOC_SEARCH_ID, this, false)
        }
    }

}