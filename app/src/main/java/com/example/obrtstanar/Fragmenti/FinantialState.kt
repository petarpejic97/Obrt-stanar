package com.example.obrtstanar.Fragmenti

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.example.obrtstanar.Klase.Controllers.WebViewController
import com.example.obrtstanar.Klase.PreferenceManager
import com.example.obrtstanar.R

class FinantialState : Fragment() {
    private lateinit var rootView : View
    private lateinit var webView : WebView
    private lateinit var preferenceManager: PreferenceManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_finantial_state, container, false)

        preferenceManager = PreferenceManager()

        setUpView()

        return rootView
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun setUpView(){
        webView = rootView.findViewById(R.id.wvFinancijalState)
        webView.settings.javaScriptEnabled = true
        if(preferenceManager.getUserType() == "Stanar"){
            webView.loadUrl("http://www.obrt-stanar.hr/prijava.php")
        }
        else {
            webView.loadUrl("http://www.obrt-stanar.hr/prijava_p.php")
        }
        webView.webViewClient = WebViewController()
    }
}