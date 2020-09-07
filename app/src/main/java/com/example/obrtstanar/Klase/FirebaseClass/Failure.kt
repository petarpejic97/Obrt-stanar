package com.example.obrtstanar.Klase.FirebaseClass

data class Failure(
    var name: String, var lastName: String, var address: String, var phoneNumber : String,
    var repairTime: String, var typeOfFailure: String, var failureDescription : String,
    var failureImageUri : String, var repairState : String, var user : String
    ){}