package com.stanar.obrtstanar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel : ViewModel() {
    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

}