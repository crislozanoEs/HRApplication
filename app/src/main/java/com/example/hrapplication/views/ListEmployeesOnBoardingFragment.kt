package com.example.hrapplication.views

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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

class ListEmployeesOnBoardingFragment() : Fragment(), EmployeeItemAdapter.OnItemLClickListener {

    private val EMPLOYEES_LIST = "employeesList"

    /**
     * The Menu mas recycler.
     */
    @Nullable
    @BindView(R.id.section_list_employees_on_boarding)
    @JvmField var sectionEmployeesBoarding: RecyclerView ?= null

    private var listEmployees: MutableList<Employee> ?= null

    internal var unbinder: Unbinder ?= null


    fun newInstance(itemsLinks: ArrayList<Employee>): ListEmployeesOnBoardingFragment {
        val fragment = ListEmployeesOnBoardingFragment()
        val args = Bundle()
        args.putSerializable(EMPLOYEES_LIST, itemsLinks)
        fragment.arguments = args
        return fragment
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val argumentsVariable = arguments
        if (argumentsVariable != null) {
            this.listEmployees = argumentsVariable.get(EMPLOYEES_LIST) as (MutableList<Employee>)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_list_employees_on_boarding, container, false)

        unbinder = ButterKnife.bind(this, view)
        val adapterOnBoarding = EmployeeItemAdapter(listEmployees!!, this)
        sectionEmployeesBoarding?.layoutManager = LinearLayoutManager(context)
        sectionEmployeesBoarding?.adapter = adapterOnBoarding
        return view
    }

    fun onButtonPressed(uri: Uri) {

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
        var mainActivity: MainActivity = context as MainActivity
        mainActivity.showEmployeeDetail(itemEmployee)
    }

}