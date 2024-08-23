package com.example.jobsearch_test.core.utils

fun List<*>?.hasNoEmptyValues(): Boolean {
    return this!!.all { it != "" }
}