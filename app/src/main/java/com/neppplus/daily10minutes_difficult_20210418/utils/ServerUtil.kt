package com.neppplus.daily10minutes_difficult_20210418.utils

import android.content.Context
import okhttp3.*
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import org.json.JSONObject
import java.io.IOException

class ServerUtil {

//    화면 입장에서, 서버에 다녀오면 할 행동 가이드북을 Interface 에 적음
    interface JsonResponseHandler {
        fun onResponse(jsonObj : JSONObject)
    }

    companion object {

        val HOST_URL = "http://15.164.153.174"

//        서버에 로그인 요청하는 기능

       fun postRequestLogin(email : String, pw : String, handler : JsonResponseHandler?) {

            val urlString = "${HOST_URL}/user"  //어느 주소로 가야하나?

            val formData = FormBody.Builder()  //POST-FormData 에 데이터 첨부
                .add("email", email)    // 왼쪽이 이름표, 오른쪽은 실제 값
                .add("password", pw)
                .build()

           val request = Request.Builder()  // 모든 정보들을 들고, 어떤 메쏘드야? = post
               .url(urlString)  // 어디로 가는지?
               .post(formData)  // POST 방식  - 필요 데이터(formData) 들고 가도록
               .build()

//           정리된 정보 들고, 실제 API 요청 진행하자 => 클라이언트로써 동작하는 코드를 쉽게 작성하도록 도와주는 라이브러리 : OkHttp
           val client = OkHttpClient()
           client.newCall(request).enqueue(object : Callback { // 갔다와서 뭐할지 가이드북을 적어주자 = 응답할수있게끔
               override fun onFailure(call: Call, e: IOException) {

               }

               override fun onResponse(call: Call, response: Response) {

//                   서버의 응답을 받아내는데 성공한 경우!
//                   응답 (response) > 내부의 본문 (body)만 활용 > String 형태로 저장해보자

                   val bodyString = response.body!!.string()

                   val jsonObj = JSONObject(bodyString)

//                   받아낸 서버의 응답 내용은 ServerUtil에서 활용 X
//                   화면에서 UI에 반영하기 위한 재료로 사용할거임 => 로그인 실패 토스트는 메인화면에서 띄움

//                   완성한 jsonObj 변수를 액티비티에 넘겨주자 (파싱 등의 처리는 액티비티에서 작성)
                   handler?.onResponse(jsonObj)
               }


           })


        }

//        회원가입 기능

        fun putRequestSignUp(email : String, pw : String, nickname : String, handler: JsonResponseHandler?) {

            val urlString = "${HOST_URL}/user"

            val formData = FormBody.Builder()
                .add("email", email)
                .add("password", pw)
                .add("nick_name", nickname)
                .build()

            val request = Request.Builder()
                .url(urlString)
                .put(formData)
                .build()

            val client = OkHttpClient()
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {
                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    handler?.onResponse(jsonObj)
                }


            })
        }

//        이메일 중복 체크 기능

        fun getRequestEmailCheck(email: String, handler: JsonResponseHandler?) {

//            GET => 어디로? 어떤 데이터? URL을 만들 때 한꺼번에 모두 적어주는 QUERY => 복잡하니까 OkHttp 라이브러리

            val urlBuilder = "${HOST_URL}/email_check".toHttpUrlOrNull()!!.newBuilder()

//            만들어진 기초 URL 에 필요한 파라미터들을 붙여주자
            urlBuilder.addEncodedQueryParameter("email", email)

//            정보 다 붙었으면 최종 String 형태로 변환
            val urlString = urlBuilder.build().toString()

            val request = Request.Builder()
                .url(urlString)
                .get()
                .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object :Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    handler?.onResponse(jsonObj)
                }


            })
        }

//        프로젝트 목록 받아오기

        fun getRequestProjectList(context : Context, handler: JsonResponseHandler?) {

//            GET => 어디로? 어떤 데이터? URL을 만들 때 한꺼번에 모두 적어주는 QUERY => 복잡하니까 OkHttp 라이브러리

            val urlBuilder = "${HOST_URL}/project".toHttpUrlOrNull()!!.newBuilder()

//            만들어진 기초 URL 에 필요한 파라미터들을 붙여주자
//            urlBuilder.addEncodedQueryParameter("email", email)

//            정보 다 붙었으면 최종 String 형태로 변환
            val urlString = urlBuilder.build().toString()

            val request = Request.Builder()
                .url(urlString)
                .get()
                .header("X-Http-Token", ContextUtil.getLoginToken(context))
                .build()

            val client = OkHttpClient()

            client.newCall(request).enqueue(object :Callback {
                override fun onFailure(call: Call, e: IOException) {

                }

                override fun onResponse(call: Call, response: Response) {

                    val bodyString = response.body!!.string()
                    val jsonObj = JSONObject(bodyString)
                    handler?.onResponse(jsonObj)
                }
            })
        }
    }
}