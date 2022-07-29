package com.example.smarthealthconsultingapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.adapter.DoctorAdapter
import com.example.smarthealthconsultingapp.model.DoctorModel
import com.example.smarthealthconsultingapp.utils.Constants
import com.example.smarthealthconsultingapp.utils.getDocList
import kotlinx.android.synthetic.main.fragment_search_doctor.*

class SearchDoctorFragment: BaseFragment(), View.OnClickListener{
    override fun getLayoutRes(): Int {
        return R.layout.fragment_search_doctor
    }

    private var adapter: DoctorAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            popBackStack()
        }
        adapter = DoctorAdapter(getDocList(requireContext()), requireContext())
        setUpRecyclerView()
        cvSearch.setOnClickListener(this)
        ivCancel.setOnClickListener(this)
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
            putParcelableArrayList(Constants.DOC_MODEL_LIST, getDocList(requireContext()))
            addFragment(Constants.DOC_SEARCH_ID, this, false)
        }
    }

}