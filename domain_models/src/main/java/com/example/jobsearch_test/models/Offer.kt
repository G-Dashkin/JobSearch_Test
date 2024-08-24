package com.example.jobsearch_test.models

data class Offer(
    val id: String? = null,
    val title: String,
    val button: Pair<String, String>,
    val link: String
)
