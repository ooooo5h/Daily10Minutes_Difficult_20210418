package com.neppplus.daily10minutes_difficult_20210418

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.neppplus.daily10minutes_difficult_20210418.adapters.ProjectAdapter
import com.neppplus.daily10minutes_difficult_20210418.datas.Project
import com.neppplus.daily10minutes_difficult_20210418.utils.ContextUtil
import com.neppplus.daily10minutes_difficult_20210418.utils.ServerUtil
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject

class MainActivity : BaseActivity() {

    val mProjects = ArrayList<Project>()
    lateinit var mProjectAdapter: ProjectAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setupEvents()
        setValues()
    }

    override fun setupEvents() {

       logoutBtn.setOnClickListener {

           val alert = AlertDialog.Builder(mContext)
           alert.setTitle("로그아웃")
           alert.setMessage("정말 로그아웃 하시겠습니까?")
           alert.setPositiveButton("확인", DialogInterface.OnClickListener { dialogInterface, i ->

               ContextUtil.setLoginToken(mContext, "")

               val myIntent = Intent(mContext, LoginActivity::class.java)
               startActivity(myIntent)

               finish()

           })
           alert.setNegativeButton("취소", null)
           alert.show()
       }



    }

    override fun setValues() {

//        서버에서 보여줄 프로젝트 목록에 어떤것들이 있는지 받아서
//        => Project() 형태로 변환하고
//        => mProjects 에 하나하나 추가하자

        getProjectListFromServer()

        mProjectAdapter = ProjectAdapter(mContext, R.layout.project_list_item, mProjects)
        projectListView.adapter = mProjectAdapter

    }

    fun getProjectListFromServer() {

//        실제로 서버에서 받아오는 기능은 따로 만들자 코드정리겸

        ServerUtil.getRequestProjectList(mContext, object : ServerUtil.JsonResponseHandler {
            override fun onResponse(jsonObj: JSONObject) {

                val dataObj = jsonObj.getJSONObject("data")
                val projectsArr = dataObj.getJSONArray("projects")

//                반복문 for 문으로 projectsArr 내부를 하나씩 꺼내서 파싱하자

                for (i in 0 until projectsArr.length()) {

//                     [ ] 안에 있는 { } 들을, 하나씩 꺼낸 뒤, 파싱 : Project 형태로 변환 후, mProject에 추가
                    val projectObj = projectsArr.getJSONObject(i)

//                     파싱 후 mProjects 에 추가하려고 하는데
//                     Project() 에 오류가 생김.
//                     Project 클래스에 보조 생성자 추가해서 재료없이도 만들 수 있게 하자

//                     Project 클래스에 함수(기능) 추가 => JSONObject를 넣으면 Project형태로 변환해주는 기능
//                     다른 화면에서도 쓸 기능이니까 Project에 함수로 만드는 거임.
                    val project = Project.getProjectFromJson(projectObj)

//                    mProject에 추가하자
                    mProjects.add(project)

                }

//                서버 통신이 어댑터 연결보다 먼저 실행되는데, 실제로는 더 늦게 끝날 수도 있다
//                mProjects.add(project) 데이터 추가가 리스트뷰의 내용 변경일 수도 있으니 notifyDataSetChanged 실행해주자

                runOnUiThread {
                    mProjectAdapter.notifyDataSetChanged()
                }

            }
        })
    }
}