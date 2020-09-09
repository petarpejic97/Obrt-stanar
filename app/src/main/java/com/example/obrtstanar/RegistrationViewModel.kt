package com.example.obrtstanar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

public class RegistrationViewModel : ViewModel() {
    var name = MutableLiveData<String>()
    var lastname = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var phoneNumber = MutableLiveData<String>()
    var password = MutableLiveData<String>()
    var confPass = MutableLiveData<String>()
}