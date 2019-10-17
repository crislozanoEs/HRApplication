package com.example.hrapplication.Model

import java.io.Serializable

class Employee() : Serializable{
    var nuevo: Boolean = false
    var id: Int = 0
    var name: String = ""
    var position: String = ""
    var salary: String = ""
    var phone: String = ""
    var email: String  = ""
    var upperRelation: Int = 0
}