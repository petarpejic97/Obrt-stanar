package com.example.obrtstanar.Fragmenti.Listener

interface FailureListener {
    fun onShowDetails(typeoffailure : String,imgUri : String)
    fun updateWithId(id: String,state : String)
    fun deleteImage(uri : String, id : String)
}