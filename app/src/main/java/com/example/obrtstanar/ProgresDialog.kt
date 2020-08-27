package com.example.obrtstanar

import android.app.ProgressDialog
import android.content.Context

class ProgressDialog(context : Context, title:String, subtitle:String) {
    var context : Context = context
    var title : String = title
    var subtitle : String = subtitle
    init{
        var progressDialog = ProgressDialog(context)
        progressDialog.setTitle(title)
        progressDialog.setMessage(subtitle)
        progressDialog.show()
    }
}