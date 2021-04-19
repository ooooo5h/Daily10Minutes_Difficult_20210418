package com.neppplus.daily10minutes_difficult_20210418

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.neppplus.daily10minutes_difficult_20210418.utils.ContextUtil

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

    }

    override fun setValues() {

        val myHandler = Handler(Looper.getMainLooper())
        myHandler.postDelayed({

            val myIntent: Intent

            if (ContextUtil.getAutoLogin(mContext) && ContextUtil.getLoginToken(mContext) != "") {

                myIntent = Intent(mContext, MainActivity::class.java)
            }

            else {
                myIntent = Intent(mContext, LoginActivity::class.java)
            }

            startActivity(myIntent)
            finish()


        }, 2500)




    }
}