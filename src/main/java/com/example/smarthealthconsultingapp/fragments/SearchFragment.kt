package com.example.smarthealthconsultingapp.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.activity.addCallback
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.adapter.DoctorAdapter
import com.example.smarthealthconsultingapp.model.DoctorModel
import com.example.smarthealthconsultingapp.utils.Constants
import com.example.smarthealthconsultingapp.utils.getDocList
import com.example.smarthealthconsultingapp.utils.makeGone
import com.example.smarthealthconsultingapp.utils.makeVisible
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search_doctor.*

class SearchFragment: BaseFragment(), TextWatcher, View.OnClickListener {
    override fun getLayoutRes(): Int {
        return R.layout.fragment_search
    }

    private var list = arrayListOf<DoctorModel>()

    private var adapter: DoctorAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        list = arguments?.getParcelableArrayList(Constants.DOC_MODEL_LIST)?: arrayListOf()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        requireActivity().onBackPressedDispatcher.addCallback(this) {
            popBackStack()
        }
        etSearch.requestFocus()
        adapter = DoctorAdapter(list, requireContext())
        setUpRV()
        ivClear.setOnClickListener(this)
        ivBack.setOnClickListener(this)
        etSearch.addTextChangedListener(this)
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun setUpRV() {
        rvSearchDoc.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = this@SearchFragment.adapter
            adapter?.notifyDataSetChanged()
        }
    }

    override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

    override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
        val it = etSearch.text
        if(it.isNotEmpty()) ivClear.makeVisible()
        else ivClear.makeGone()
    }

    override fun afterTextChanged(et: Editable?) {}

    override fun onClick(view: View?) {
        when(view?.id) {
            R.id.ivClear -> etSearch.setText("")
            R.id.ivBack -> popBackStack()
        }
    }
}