package com.stanar.obrtstanar.Fragmenti.Listener

interface FailureListener {
    fun onShowDetails(typeoffailure : String,imgUri : String)
    fun updateWithId(id: String,state : String)
    fun deleteImage(uri : String, id : String)
    fun callRepairer(typeoffailure: String)
}