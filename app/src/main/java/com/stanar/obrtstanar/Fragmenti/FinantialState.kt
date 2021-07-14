package com.stanar.obrtstanar.Fragmenti

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import androidx.fragment.app.Fragment
import com.stanar.obrtstanar.Klase.Controllers.WebViewController
import com.stanar.obrtstanar.Klase.PreferenceManager
import com.stanar.obrtstanar.R

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
        Log.w("usertype",preferenceManager.getUserType().toString())
        if(preferenceManager.getUserType() == "Stanar"){

            webView.loadUrl("http://www.obrt-stanar.hr/prijava.php")
        }
        else {
            webView.loadUrl("http://www.obrt-stanar.hr/prijava_p.php")
        }
        webView.webViewClient = WebViewController()
    }
}