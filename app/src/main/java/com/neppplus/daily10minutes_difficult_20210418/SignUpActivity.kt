package com.neppplus.daily10minutes_difficult_20210418

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.neppplus.daily10minutes_difficult_20210418.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_sign_up.*
import org.json.JSONObject

class SignUpActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

//        이메일 입력칸의 내용 변경이 생기면 => 무조건 다시 검사하도록 문구를 변경하자자

       emailCheckBtn.setOnClickListener {

            val inputEmail = emailEdt.text.toString()

            ServerUtil.getRequestEmailCheck(inputEmail, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

                    val code = jsonObj.getInt("code")

                    runOnUiThread {
                        if(code == 200) {
                            emailCheckResultTxt.text = "사용해도 좋은 이메일입니다"
                        }
                        else {
                            emailCheckResultTxt.text = "중복된 이메일이라 사용할 수 없습니다."
                        }
                    }
                }
            })
        }

        signUpBtn.setOnClickListener {

            val inputEmail = emailEdt.text.toString()
            val inputPw = passwordEdt.text.toString()
            val inputNickname = nicknameEdt.text.toString()

            ServerUtil.putRequestSignUp(inputEmail, inputPw, inputNickname, object : ServerUtil.JsonResponseHandler {
                override fun onResponse(jsonObj: JSONObject) {

                    val code = jsonObj.getInt("code")
                    if (code == 200) {

                        runOnUiThread {
                            Toast.makeText(mContext, "회원가입을 환영합니다", Toast.LENGTH_SHORT).show()
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

    }


}