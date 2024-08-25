package com.example.jobsearch_test.vacancy.presentation

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.vacancy.R
import com.example.vacancy.databinding.QuestionItemBinding

class QuestionsListAdapter(private val context: Context, private val questions: List<String>) : BaseAdapter() {

    override fun getCount(): Int = questions.size

    override fun getItem(position: Int): Any = questions[position]

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var row = convertView
        var holder: QuestionViewHolder? = null

        if (row == null) {
            val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            row = inflater.inflate(R.layout.question_item, parent, false)
            row.tag = QuestionViewHolder(binding = QuestionItemBinding.inflate(LayoutInflater.from(parent?.context), parent, false))
        } else {
            holder = row.tag as QuestionViewHolder
        }

//        Log.d("MyLog", questions.toString())
        Log.d("MyLog", questions[position])

        holder?.question?.text = questions[position]
        return row!!
    }

    private class QuestionViewHolder(private val binding: QuestionItemBinding) {
        val question = binding.question
    }
}