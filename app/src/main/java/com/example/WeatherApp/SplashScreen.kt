package com.example.WeatherApp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.splash_screen.*

class SplashScreen : AppCompatActivity() {

    private val DELAY: Long = 2500
    private var mDelayHandler: Handler? = null
    private var progressBarStatus = 0
    var start:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)
        mDelayHandler = Handler()
        mDelayHandler!!.postDelayed(mRunnable, DELAY)
    }

    private fun StartingMainActivity() {
        var intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
        startActivity(intent)
        this.finish()
        mDelayHandler!!.removeCallbacks(mRunnable)

    }

    private val mRunnable: Runnable = Runnable {

        Thread(Runnable {
            while (progressBarStatus < 100) {
                try {
                    start += 15
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progressBarStatus = start
                splash_screen_progress_bar.progress = progressBarStatus
            }
            StartingMainActivity()
        }).start()
    }
}