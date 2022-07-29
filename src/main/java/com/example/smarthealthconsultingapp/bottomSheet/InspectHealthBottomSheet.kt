package com.example.smarthealthconsultingapp.bottomSheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.adapter.SymptomAdapter
import com.example.smarthealthconsultingapp.utils.getList
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.fragment_inspect_health.*

class InspectHealthBottomSheet: BottomSheetDialogFragment() {

    private var adapter : SymptomAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return LayoutInflater.from(requireContext()).inflate(R.layout.fragment_inspect_health, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = SymptomAdapter(requireContext(), getSymptomList())
        setUpRecyclerView()
    }

    private fun getSymptomList(): ArrayList<String> {
        return getList()
    }

    private fun setUpRecyclerView() {
        rvSymptomps.apply {
            layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
            adapter = this@InspectHealthBottomSheet.adapter
        }
    }
}