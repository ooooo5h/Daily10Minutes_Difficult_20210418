package com.neppplus.daily10minutes_difficult_20210418

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.neppplus.daily10minutes_difficult_20210418.datas.Project

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

    }

}