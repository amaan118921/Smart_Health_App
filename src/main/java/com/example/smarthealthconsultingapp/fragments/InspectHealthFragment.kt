package com.example.smarthealthconsultingapp.fragments

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.adapter.SymptomAdapter
import com.example.smarthealthconsultingapp.utils.getList
import kotlinx.android.synthetic.main.fragment_inspect_health.*

class InspectHealthFragment: BaseFragment(), View.OnClickListener {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_inspect_health
    }

    private var adapter : SymptomAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            popBackStack()
        }
        adapter = SymptomAdapter(requireContext(), getSymptomList())
        setUpRecyclerView()
        ivCancel.setOnClickListener(this)
    }

    private fun getSymptomList(): ArrayList<String> {
        return getList()
    }

    private fun setUpRecyclerView() {
        rvSymptomps.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = this@InspectHealthFragment.adapter
        }
    }

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.btnSubmit -> submitAndFetchResult()
            R.id.ivCancel -> popBackStack()
        }
    }

    private fun submitAndFetchResult() {
        val list = adapter?.getList()
    }


}