package com.example.hrapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.hrapplication.Model.Employee
import com.example.hrapplication.views.EmployeeDetailFragment
import com.example.hrapplication.views.ListEmployeesFragment
import java.net.URL

class MainActivity : AppCompatActivity() {

    var btnInit: Button ?= null
    var listEmployees: ArrayList<Employee> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnInit = findViewById<Button>(R.id.btn_init_app)
        btnInit?.setOnClickListener { callResources() }
    }

    fun callResources(){
        Log.e("CLICK", "Button")

        //val apiResponse = URL("https://raw.githubusercontent.com/slozanomuy/Services/master/RH.json").readText()
        var emp1: Employee = Employee()
        emp1.name = "AAA"
        emp1.id = 22
        emp1.email = "a"
        emp1.phone = "22"
        emp1.salary = "22"
        emp1.position = "AA"
        emp1.upperRelation = 0
        emp1.nuevo = false

        var emp2: Employee = Employee()
        emp2.name = "BB"
        emp2.id = 23
        emp2.email = "a"
        emp2.phone = "22"
        emp2.salary = "22"
        emp2.position = "AA"
        emp2.upperRelation = 22
        emp2.nuevo = false

        listEmployees = arrayListOf(emp1,emp2)

        val listEmployeesFragment: ListEmployeesFragment = ListEmployeesFragment().newInstance(listEmployees)
        supportFragmentManager.inTransaction {
            replace(R.id.insert_point, listEmployeesFragment)
        }

    }

    fun updateStateEmployee(id: Int){
        for(element in listEmployees){
            if(element.id == id){
                element.nuevo = !element.nuevo
            }
        }
    }
    fun showEmployeeDetail(employee: Employee){
        val listSubEmployee: ArrayList<Employee> = ArrayList()
        for(element in listEmployees){
            Log.e("EMPLOYEE", element.id.toString() +" " + element.upperRelation.toString() )
            Log.e("EMPLOYEE SELECTED", employee.id.toString())
            if(element.upperRelation==employee.id){
                Log.e("EMPLOYEE","SBUM")
                listSubEmployee.add(element)
            }
        }
        val employeeDetailFragment: EmployeeDetailFragment = EmployeeDetailFragment().newInstance(employee= employee, itemsLinks = listSubEmployee)
        supportFragmentManager.inTransaction {
            replace(R.id.insert_point, employeeDetailFragment)
        }
    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }
}
