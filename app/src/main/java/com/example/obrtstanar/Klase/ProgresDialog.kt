package com.example.obrtstanar.Klase

import android.app.ProgressDialog
import android.content.Context

class ProgressDialog(var context: Context, var title: String, private var subtitle: String) {
    val progressDialog = ProgressDialog(context)
    init{
        progressDialog.setTitle(title)
        progressDialog.setMessage(subtitle)
    }
    fun showDialog(){
        progressDialog.show()
    }
    fun progresDismis(){
        progressDialog.dismiss();
    }
}