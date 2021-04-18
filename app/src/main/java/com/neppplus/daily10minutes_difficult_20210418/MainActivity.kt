package com.neppplus.daily10minutes_difficult_20210418

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neppplus.daily10minutes_difficult_20210418.datas.Project
import com.neppplus.daily10minutes_difficult_20210418.utils.ServerUtil
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val mProjects = ArrayList<Project>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }
    override fun setupEvents() {

    }

    override fun setValues() {

//        서버에서 보여줄 프로젝트 목록에 어떤것들이 있는지 받아서
//        => Project() 형태로 변환하고
//        => mProjects 에 하나하나 추가하자

        getProjectListFromServer()
    }

    fun getProjectListFromServer() {

//        실제로 서버에서 받아오는 기능은 따로 만들자 코드정리겸

        ServerUtil.getRequestProjectList(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {

                val dataObj = jsonObj.getJSONObject("data")
                val projectsArr = dataObj.getJSONArray("projects")

//                반복문 for 문으로 projectsArr 내부를 하나씩 꺼내서 파싱하자

                for (i in 0 until projectsArr.length()) {

//                     [ ] 안에 있는 { } 들을, 하나씩 꺼낸 뒤,
                    val projectObj = projectsArr.getJSONObject(i)

//                     파싱 후 mProjects 에 추가하려고 하는데
//                     Project() 에 오류가 생김.
//                     Project 클래스에 보조 생성자 추가해서 재료없이도 만들 수 있게 하자

//                     Project 클래스에 함수(기능) 추가 => JSONObject를 넣으면 Project형태로 변환해주는 기능
//                     다른 화면에서도 쓸 기능이니까 Project에 함수로 만드는 거임.
                    val project = Project.getProjectFromJson(projectObj)

                }
            }
        })
    }
}