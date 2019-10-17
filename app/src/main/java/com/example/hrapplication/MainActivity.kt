package com.example.hrapplication


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import java.net.URL

class MainActivity : AppCompatActivity() {

    val btnInit: Button = findViewById(R.id.btn_init_app)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btnInit.setOnClickListener { callResources() }
    }

    fun callResources(){
        Log.e("CLICK", "Button")

        val apiResponse = URL("https://raw.githubusercontent.com/slozanomuy/Services/master/RH.json").readText()

        supportFragmentManager.inTransaction {
            //add(R.id.insert_point, fragment)
        }

    }

    inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> Unit) {
        val fragmentTransaction = beginTransaction()
        fragmentTransaction.func()
        fragmentTransaction.commit()
    }
}
