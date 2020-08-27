package com.example.obrtstanar

import android.app.ProgressDialog
import android.content.Context

class ProgressDialog(var context: Context, var title: String, private var subtitle: String) {
    val progressDialog = ProgressDialog(context)
    init{
        progressDialog.setTitle(title)
        progressDialog.setMessage(subtitle)
        progressDialog.show()
    }
    fun progresDismis(){
        progressDialog.dismiss();
    }
}