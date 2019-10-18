package com.example.hrapplication


import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.hrapplication.Model.Employee
import com.example.hrapplication.views.EmployeeDetailFragment
import com.example.hrapplication.views.ListEmployeesFragment
import org.json.JSONObject

import java.net.URL
import android.os.StrictMode
import android.os.AsyncTask
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.hrapplication.views.ListEmployeesOnBoardingFragment
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection


class MainActivity : AppCompatActivity() {

    var btnInit: Button ?= null
    var btnNuevos: Button ?= null
    var listEmployees: ArrayList<Employee> = ArrayList()
    var load: Boolean = true
    val MY_PERMISSIONS_REQUEST_INTERNET: Int = 201
    var context: Context ?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val policy = StrictMode.ThreadPolicy.Builder().permitAll().build()
        StrictMode.setThreadPolicy(policy)
        context = baseContext
        btnInit = findViewById<Button>(R.id.btn_init_app)
        btnNuevos = findViewById<Button>(R.id.btn_list_nuevos)

        btnNuevos?.setOnClickListener { showOnBoarding() }

        if (ContextCompat.checkSelfPermission(baseContext,
                Manifest.permission.INTERNET)
            != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
            } else {
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.INTERNET),
                    MY_PERMISSIONS_REQUEST_INTERNET)
            }
        } else {
            btnInit?.setOnClickListener { Download().execute(URL("https://raw.githubusercontent.com/slozanomuy/Services/master/RH.json")) }
        }

    }

    fun showOnBoarding(){
        var listNuevos: ArrayList<Employee> = ArrayList()
        for(element in listEmployees){
            if(element.nuevo){
                listNuevos.add(element)
            }
        }
        if(listNuevos.size !=0 ){
            val listEmployeesFragment: ListEmployeesOnBoardingFragment = ListEmployeesOnBoardingFragment().newInstance(listNuevos)
            supportFragmentManager.inTransaction {
                replace(R.id.insert_point, listEmployeesFragment)
            }
        }else{
            Toast.makeText(context,"NO HAY EMPLEADOS NUEVOS", Toast.LENGTH_LONG).show()
        }
    }

    inner class Download : AsyncTask<URL, Int, String>() {

        private var result: String = ""

        override fun doInBackground(vararg params: URL?): String {

            val connect = params[0]?.openConnection() as HttpURLConnection
            connect.readTimeout = 8000
            connect.connectTimeout = 8000
            connect.requestMethod = "GET"
            connect.connect()

            val responseCode: Int = connect.responseCode;
            if (responseCode == 200) {
                result = streamToString(connect.inputStream)
            }

            return result
        }


        override fun onProgressUpdate(vararg values: Int?) {
            for (it in values) {
            }
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            when {
                result != null -> handleJson(result)
                else -> {
                    Toast.makeText(context,"ERROR AL CARGAR INFORMACION", Toast.LENGTH_LONG).show()
                }
            }
        }

        private fun handleJson(jsonString: String) {
            callResources(jsonString)
        }
    }

    fun streamToString(inputStream: InputStream): String {

        val bufferReader = BufferedReader(InputStreamReader(inputStream))
        var line: String
        var result = ""

        try {
            do {
                line = bufferReader.readLine()
                if (line != null) {
                    result += line
                }
            } while (line != null)
            inputStream.close()
        } catch (ex: Exception) {

        }
        return result
    }

    fun callResources(jsonString: String){
        if(load){
            val jsonObj = JSONObject(jsonString.substring(jsonString.indexOf("{"), jsonString.lastIndexOf("}") + 1))
            for(i in 0 until jsonObj.names().length()){
                var employee: Employee = Employee()
                employee.name = jsonObj.names().get(i).toString()
                employee.email= jsonObj.getJSONObject(jsonObj.names().get(i) as String).getString("email")
                employee.id =  jsonObj.getJSONObject(jsonObj.names().get(i) as String).getInt("id")
                employee.position = jsonObj.getJSONObject(jsonObj.names().get(i) as String).getString("position")
                employee.salary = jsonObj.getJSONObject(jsonObj.names().get(i) as String).getString("salary")
                employee.phone = jsonObj.getJSONObject(jsonObj.names().get(i) as String).getString("phone")
                employee.upperRelation = jsonObj.getJSONObject(jsonObj.names().get(i) as String).getInt("upperRelation")
                listEmployees.add(employee)

            }
            btnNuevos?.isEnabled = true
            load = false
        }
        val listEmployeesFragment: ListEmployeesFragment = ListEmployeesFragment().newInstance(listEmployees)
        supportFragmentManager.inTransaction {
            replace(R.id.insert_point, listEmployeesFragment)
        }

    }

    fun updateStateEmployee(id: Int){
        for((index, element) in listEmployees.withIndex()){
            if(element.id == id){
                listEmployees[index].nuevo = !listEmployees[index].nuevo
                Toast.makeText(baseContext, "Empleado Actualizado", Toast.LENGTH_LONG).show()
            }
        }
    }
    fun showEmployeeDetail(employee: Employee){
        val listSubEmployee: ArrayList<Employee> = ArrayList()
        for(element in listEmployees){
            if(element.upperRelation==employee.id){
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
