package com.stanar.obrtstanar.Klase.Controllers

import android.widget.EditText

class AlertController {
    fun twoEditTextAlert( editTextOne : EditText, editTextTwo : EditText, errorTextOne : String){
        editTextOne.error = errorTextOne
        editTextTwo.error = errorTextOne
    }
    fun oneEditTextAlert(editTextOne : EditText, errorTextOne : String){
        editTextOne.error = errorTextOne
    }
}