package com.example.jobsearch_test.vacancy.presentation

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.vacancy.R

class QuestionsListAdapter(private val context: Context, private val questions: List<String>) :
    ArrayAdapter<String>(context, 0, questions){

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.question_item, parent, false)
        }
        val item = questions[position]
        val textView = view!!.findViewById<TextView>(R.id.question)

        textView.text = item
        return view
    }

}