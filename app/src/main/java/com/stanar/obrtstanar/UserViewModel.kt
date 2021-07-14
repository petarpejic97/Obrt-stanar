package com.stanar.obrtstanar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

public class UserViewModel : ViewModel() {
    var name = MutableLiveData<String>()
    var lastname = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var phoneNumber = MutableLiveData<String>()
}

