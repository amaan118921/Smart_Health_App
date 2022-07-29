package com.example.smarthealthconsultingapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthealthconsultingapp.R
import com.google.android.material.chip.Chip

class SymptomAdapter(private val context: Context, private val list: ArrayList<String>): RecyclerView.Adapter<SymptomAdapter.SymptomViewHolder>()  {

    private var checkList: ArrayList<Int> = arrayListOf()

    class SymptomViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val chip: Chip = view.findViewById(R.id.symptomChip)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SymptomViewHolder {
        return SymptomViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view__symptoms, parent,false))
    }

    @SuppressLint("UseCompatLoadingForColorStateLists", "NotifyDataSetChanged", "ResourceAsColor")
    override fun onBindViewHolder(holder: SymptomViewHolder, position: Int) {
       holder.apply {
           if(checkList.contains(position)) {
               chip.chipBackgroundColor = context.resources.getColorStateList(R.color.app_theme)
               chip.setTextColor(context.resources.getColorStateList(R.color.white))
           }
           else {
               chip.chipBackgroundColor = context.resources.getColorStateList(R.color.off_black)
               chip.setTextColor(context.resources.getColorStateList(R.color.black))
           }
           chip.text = list[position]
           chip.setOnClickListener {
               if(checkList.contains(position)) checkList.remove(position)
               else checkList.add(position)
               notifyItemChanged(position)
           }
       }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun getList(): ArrayList<Int> {
        return checkList
    }

}