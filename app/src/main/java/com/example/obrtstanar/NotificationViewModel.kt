package com.example.obrtstanar

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

public class NotificationViewModel : ViewModel() {
    var title = MutableLiveData<String>()
    var notificationText = MutableLiveData<String>()
}
