package com.example.hrapplication.manager

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.hrapplication.Model.Employee
import com.example.hrapplication.R

import butterknife.BindView
import butterknife.ButterKnife
import com.example.hrapplication.manager.EmployeeItemAdapter.ViewHolder
import java.util.*
import kotlin.collections.ArrayList

class EmployeeItemAdapter() : RecyclerView.Adapter<ViewHolder>() {



    var arraylist: ArrayList<Employee>?=null
    var listEmployee: MutableList<Employee>? = null
    var onItemLClickListener: OnItemLClickListener? = null


    constructor(
        listEmployees: MutableList<Employee>,
        onItemLClickListener: OnItemLClickListener
    ) : this() {
        this.listEmployee = listEmployees
        this.onItemLClickListener = onItemLClickListener
        this.arraylist = ArrayList()
        this.arraylist?.addAll(listEmployee!!)
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.item_list_employee, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull viewHolder: ViewHolder, i: Int) {
        viewHolder.nameItem?.text = listEmployee!![i].name
        viewHolder.idItem?.text = listEmployee!![i].id.toString()
        viewHolder.setClickListener(listEmployee!![i], onItemLClickListener!!)
    }

    override fun getItemCount(): Int {
        return listEmployee!!.size
    }
    fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        listEmployee?.clear()
        if (charText.length == 0) {
            for (element in this!!.arraylist!!) {
                listEmployee?.add(element)
            }
        } else {
            for (element in this!!.arraylist!!) {
                if (element.name.toLowerCase(Locale.getDefault()).contains(charText)) {
                    listEmployee?.add(element)
                }
            }
        }
        notifyDataSetChanged()
    }

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view)
   {
       @BindView(R.id.txt_id_tabla)
       @JvmField var idItem: TextView? = null

        @BindView(R.id.txt_name_tabla)
        @JvmField var nameItem: TextView? = null

       init{
           ButterKnife.bind(this, view)
       }
        fun setClickListener(itemEmployee: Employee, listener: OnItemLClickListener) {
            view.setOnClickListener { listener.onItemClick(itemEmployee) }
        }
    }

    interface OnItemLClickListener {
        fun onItemClick(itemContactUs: Employee)
    }

}