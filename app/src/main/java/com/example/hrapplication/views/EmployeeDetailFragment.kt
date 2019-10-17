package com.example.hrapplication.views

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.RadioButton
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.Unbinder
import com.example.hrapplication.MainActivity
import com.example.hrapplication.Model.Employee
import com.example.hrapplication.R
import com.example.hrapplication.manager.EmployeeItemAdapter
import java.util.ArrayList

class EmployeeDetailFragment() : Fragment(), EmployeeItemAdapter.OnItemLClickListener {

    private val EMPLOYEES_LIST = "employeesList"
    private val EMPLOYEE_DETAIL = "employeeDetail"


    @Nullable
    @BindView(R.id.section_list_sub_employees)
    @JvmField var sectionEmployees: RecyclerView ?= null

    @Nullable
    @BindView(R.id.txt_id)
    @JvmField var txtId: TextView ?= null

    @Nullable
    @BindView(R.id.txt_email)
    @JvmField var txtEmail: TextView ?= null

    @Nullable
    @BindView(R.id.txt_phone)
    @JvmField var txtPhone: TextView ?= null

    @Nullable
    @BindView(R.id.txt_position)
    @JvmField var txtPosition: TextView ?= null

    @Nullable
    @BindView(R.id.txt_salary)
    @JvmField var txtSalary: TextView ?= null

    @Nullable
    @BindView(R.id.txt_name)
    @JvmField var txtName: TextView ?= null

    @Nullable
    @BindView(R.id.radio_nuevo)
    @JvmField var radioButton: RadioButton ?= null

    @Nullable
    @BindView(R.id.btn_guardar)
    @JvmField var btnGuardar: Button ?= null


    private var listEmployees: List<Employee> ?= null

    internal var unbinder: Unbinder ?= null

    private var employee: Employee? = null


    fun newInstance(itemsLinks: ArrayList<Employee>, employee: Employee): EmployeeDetailFragment {
        val fragment = EmployeeDetailFragment()
        val args = Bundle()
        args.putSerializable(EMPLOYEES_LIST, itemsLinks)
        args.putSerializable(EMPLOYEE_DETAIL, employee)
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val argumentsVariable = arguments
        if (argumentsVariable != null) {
            this.listEmployees = argumentsVariable.get(EMPLOYEES_LIST) as (List<Employee>)
            this.employee = argumentsVariable.get(EMPLOYEE_DETAIL) as Employee
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.content_item_employee, container, false)
        unbinder = ButterKnife.bind(this, view)
        val adapter = EmployeeItemAdapter(listEmployees!!, this)
        sectionEmployees?.setLayoutManager(LinearLayoutManager(context))
        sectionEmployees?.setAdapter(adapter)
        txtId?.text = employee!!.id.toString()
        txtName?.text = employee!!.name
        txtSalary?.text = employee!!.salary
        txtPosition?.text = employee!!.position
        txtPhone?.text = employee!!.phone
        txtEmail?.text = employee!!.email
        radioButton?.isActivated = employee!!.nuevo
        radioButton?.setOnCheckedChangeListener{
                buttonView, isChecked -> changeButtonState(isChecked)
        }
        btnGuardar?.setOnClickListener { updateState() }

        return view
    }

    fun updateState (){
        var mainActivity: MainActivity = context as MainActivity
        mainActivity.updateStateEmployee(employee!!.id)
    }
    fun changeButtonState(isChecked: Boolean){
        btnGuardar!!.isEnabled = isChecked && !employee!!.nuevo || !isChecked && employee!!.nuevo

    }
    override fun onAttach(context: Context) {
        super.onAttach(context)

    }

    override fun onDetach() {
        super.onDetach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        unbinder!!.unbind()
    }


    override fun onItemClick(itemEmployee: Employee) {
    }

}