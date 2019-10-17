package com.example.hrapplication.manager

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

class EmployeeItemAdapter() : RecyclerView.Adapter<ViewHolder>() {


    var listEmployee: List<Employee>? = null
    var onItemLClickListener: OnItemLClickListener? = null


    constructor(
        listEmployees: List<Employee>,
        onItemLClickListener: OnItemLClickListener
    ) : this() {
        this.listEmployee = listEmployees
        this.onItemLClickListener = onItemLClickListener
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

    inner class ViewHolder(var view: View) : RecyclerView.ViewHolder(view)
   {
       @BindView(R.id.txt_name_tabla)
       @JvmField var nameItem: TextView? = null

        @BindView(R.id.txt_id_tabla)
        @JvmField var idItem: TextView? = null

       init{
           ButterKnife.bind(this, itemView)
       }
        fun setClickListener(itemEmployee: Employee, listener: OnItemLClickListener) {
            view.setOnClickListener { listener.onItemClick(itemEmployee) }
        }
    }

    interface OnItemLClickListener {
        fun onItemClick(itemContactUs: Employee)
    }

}