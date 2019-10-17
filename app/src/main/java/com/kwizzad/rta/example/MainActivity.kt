package com.kwizzad.rta.example

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.kwizzad.adsbase.AdOpportunity
import com.kwizzad.rta.KwizzadRta
import com.kwizzad.rta.PreloadCallback
import com.kwizzad.rta.ShowCallback

class MainActivity : AppCompatActivity() {

    private lateinit var btnShow : Button
    private lateinit var tvLog: TextView

    private lateinit var rta: KwizzadRta

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initLayout()
        initRta()
        loadRta()
    }

    private fun initLayout() {
        setContentView(R.layout.activity_main)
        btnShow = findViewById(R.id.btn_show_ad)
        tvLog = findViewById(R.id.log)
    }

    private fun initRta() {
        rta = KwizzadRta.create(this, "com.kwizzad.rta.example/rta_android")
        rta.identifyUser("com.kwizzad.rta.example:3452462")
        rta.onCreate(this)
        btnShow.setOnClickListener {
            showRta()
        }
    }

    private fun showRta() {
        rta.showAd(this, object : ShowCallback {
            override fun onAdCancelled(adOpportunity: AdOpportunity) {
                log("onAdCancelled : $adOpportunity")
            }

            override fun onAdError(error: Throwable) {
                log("onAdError : ${error.message ?: error.localizedMessage}")
            }

            override fun onAdFinished(adOpportunity: AdOpportunity) {
                log("onAdFinished : $adOpportunity")
            }

            override fun onAdOpened(adOpportunity: AdOpportunity) {
                log("onAdOpened : $adOpportunity")
            }
        })
    }


    private fun loadRta() {
        rta.setPreloadedCallback(object: PreloadCallback {
            override fun onAdAvailable(available: Boolean) {
                setAdAvailable(available)
            }

            override fun onAdFailedToLoad(error: Throwable) {
                log(error)
            }
        })
        rta.load(this, "rewa")
    }

    private fun setAdAvailable(available: Boolean) {
        val logText : String
        btnShow.isEnabled = available
        if (available) {
            logText = "Ad is loaded"
            btnShow.text = "SHOW RTA"
        } else {
            logText = "Loading ads..."
            btnShow.text = "LOADING RTA..."
        }
        log(logText)
    }


    private fun log(text: String) {
        tvLog.text =  "${tvLog.text}\n\n$text"
    }

    private fun log(throwable: Throwable) {
        tvLog.text =  "${tvLog.text}\n\n${throwable.message}"
    }

    override fun onStart() {
        super.onStart()
        rta.onStart(this)
    }

    override fun onResume() {
        super.onResume()
        rta.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        rta.onPause(this)
    }

    override fun onStop() {
        super.onStop()
        rta.onStop(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        rta.onDestroy(this)
    }
}