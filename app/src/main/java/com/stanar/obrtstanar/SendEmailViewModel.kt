package com.stanar.obrtstanar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SendEmailViewModel: ViewModel() {
    var subject = MutableLiveData<String>()
    var body = MutableLiveData<String>()
}