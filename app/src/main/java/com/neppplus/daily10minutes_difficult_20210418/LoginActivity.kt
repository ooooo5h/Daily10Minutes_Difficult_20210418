package com.neppplus.daily10minutes_difficult_20210418

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.neppplus.daily10minutes_difficult_20210418.utils.ContextUtil
import com.neppplus.daily10minutes_difficult_20210418.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_login.*
import org.json.JSONObject

class LoginActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

        autoLoginCheckBox.setOnCheckedChangeListener { compoundButton, isChecked ->

//            1-1. 변경된 체크값 저장
            ContextUtil.setAutoLogin(mContext, isChecked)

        }

        signUpBtn.setOnClickListener {

            val myIntent = Intent(mContext, SignUpActivity::class.java)
            startActivity(myIntent)

        }

        loginBtn.setOnClickListener {

            val inputEmail = emailEdt.text.toString()
            val inputPassword = passwordEdt.text.toString()

            ServerUtil.postRequestLogin(inputEmail, inputPassword, object :ServerUtil.JsonResponseHandler{
                override fun onResponse(jsonObj: JSONObject) {

                    val codeNum = jsonObj.getInt("code")
                    
                    if (codeNum == 200) {

                        val dataObj = jsonObj.getJSONObject("data")
                        val userObj = dataObj.getJSONObject("user")
                        val nickname = userObj.getString("nick_name")

//                        서버가 내려주는 토큰값 추출해서
                        val token = dataObj.getString("token")

//                        sharedPreferences 저장해서 기기에 보관 (전원이 나가도 자동로그인되게)
                        ContextUtil.setLoginToken(mContext, token)

                        runOnUiThread {
                            Toast.makeText(mContext,"${nickname}님 환영합니다", Toast.LENGTH_SHORT).show()

                            val myIntent = Intent(mContext, MainActivity::class.java)
                            startActivity(myIntent)

                            finish()
                        }
                    }
                    else {

                        val message = jsonObj.getString("message")
                        
                        runOnUiThread {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }

    override fun setValues() {

//        1-2. ContextUtil 에 저장해둔 자동로그인 여부를 꺼내서 체크박스에 반영
        autoLoginCheckBox.isChecked = ContextUtil.getAutoLogin(mContext)

    }
}