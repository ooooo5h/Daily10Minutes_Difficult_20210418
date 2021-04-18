package com.neppplus.daily10minutes_difficult_20210418.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import com.neppplus.daily10minutes_difficult_20210418.R
import com.neppplus.daily10minutes_difficult_20210418.datas.Project

class ProjectAdapter(
        val mContext : Context,
        resId : Int,
        val mList : List<Project>) : ArrayAdapter<Project>(mContext, resId, mList) {

    val inflater = LayoutInflater.from(mContext)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var tempRow = convertView

        if(tempRow == null) {
            tempRow = inflater.inflate(R.layout.project_list_item, null)
        }
        val row = tempRow!!

        val data = mList[position]

        val projectBackgroundImg = row.findViewById<ImageView>(R.id.projectBackgroundImg)
        val projectTitleTxt = row.findViewById<TextView>(R.id.projectTitleTxt)

        projectTitleTxt.text = data.title

        return row
    }
}