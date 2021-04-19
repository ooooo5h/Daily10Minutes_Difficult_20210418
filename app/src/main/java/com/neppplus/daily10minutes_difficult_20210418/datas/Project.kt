package com.neppplus.daily10minutes_difficult_20210418.datas

import org.json.JSONObject
import java.io.Serializable

class Project(
    var id: Int,
    var title: String,
    var imageUrl: String,
    var description: String,
    var onGoingUserCount : Int,
    var proofMethod : String ) : Serializable {

//        보조 생성자를 추가하자. Project() 만으로도 쓸 수 있게 하기위해서!!

    constructor() : this(0, "", "", "", 0, "")

//        기능을 추가하자
//        JSONObject 을 넣으면 (input)
//        Project 로 변환 (return)  => 단순 기능은 companion object 를 이용하는게 편함

    companion object {

        fun getProjectFromJson(jsonObj : JSONObject) : Project {

            val project = Project()

//            jsonObj 에서 정보 추출해서 project 의 하위 항목을 반영하자
            project.id = jsonObj.getInt("id")
            project.title = jsonObj.getString("title")
            project.imageUrl = jsonObj.getString("img_url")
            project.description = jsonObj.getString("description")

            project.onGoingUserCount = jsonObj.getInt("ongoing_users_count")
            project.proofMethod = jsonObj.getString("proof_method")

            return project

        }
    }
}