package com.example.obrtstanar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ReportFailureViewModel : ViewModel() {
    var name = MutableLiveData<String>()
    var lastname = MutableLiveData<String>()
    var address = MutableLiveData<String>()
    var phoneNumber = MutableLiveData<String>()
    var email = MutableLiveData<String>()
    var description = MutableLiveData<String>()
}