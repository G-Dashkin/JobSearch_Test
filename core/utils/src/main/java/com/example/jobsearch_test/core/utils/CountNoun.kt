package com.example.jobsearch_test.core.utils

fun countNoun(number: Int):Int {
    return if (number == 1) com.example.ui.R.string.vacancy
    else if (number >= 2 && number <= 4) com.example.ui.R.string.vacancies
    else com.example.ui.R.string.vacancies_
}