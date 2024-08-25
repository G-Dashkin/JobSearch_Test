package com.example.jobsearch_test.models

interface VacancyItem {
    fun id() : Any
    fun content(another: VacancyItem): Boolean
    fun payload(another: VacancyItem): PayloadChange = PayloadChange.None

    interface PayloadChange {
        object None:PayloadChange
    }
}