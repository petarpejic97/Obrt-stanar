package com.stanar.obrtstanar.Klase.Controllers

class PasswordController(var password : String, var confPassword : String) {

     fun checkPasswordEquality() : Boolean {
         return password == confPassword
    }
     fun checkPasswordRequirments() : Boolean {
        if(password.length < 6){
            return false;
        }
        else return ifContainNumber(password);
    }
    fun ifContainNumber(password : String) : Boolean{

        for (c in password.toCharArray()) {
            if (Character.isDigit(c)) {
                return true
            }
        }
        return false

    }
}