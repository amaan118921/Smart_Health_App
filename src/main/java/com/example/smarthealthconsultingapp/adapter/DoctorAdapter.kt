package com.example.smarthealthconsultingapp.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.smarthealthconsultingapp.R
import com.example.smarthealthconsultingapp.model.DoctorModel
import com.google.android.material.textview.MaterialTextView
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.android.synthetic.main.item_view_doctor.view.*

class DoctorAdapter(private val list: List<DoctorModel>, private val context: Context): RecyclerView.Adapter<DoctorAdapter.DoctorViewHolder>() {

    class DoctorViewHolder(private val view: View): RecyclerView.ViewHolder(view) {
        val docName = view.findViewById<MaterialTextView>(R.id.tvDocName)
        val speciality = view.findViewById<MaterialTextView>(R.id.tvSpeciality)
        val status = view.findViewById<MaterialTextView>(R.id.tvStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DoctorViewHolder {
        return DoctorViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_view_doctor, parent, false))
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onBindViewHolder(holder: DoctorViewHolder, position: Int) {
        holder.apply {
            docName.text = list[position].docName
            speciality.text = list[position].speciality
            if(list[position].status==1) status.text = context.getString(R.string.active)
            else status.text = context.getString(R.string.offline)
            if(list[position].status==1) holder.itemView.ivStatus.setImageDrawable(context.getDrawable(R.drawable.active))
            else holder.itemView.ivStatus.setImageDrawable(context.getDrawable(R.drawable.off))
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}