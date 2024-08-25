package com.example.jobsearch_test.models


data class Vacancy(
    val id: String,
    val lookingNumber: Int,
    val title: String,
    val address: Address,
    val company: String,
    val experience: Experience,
    val publishedDate: String,
    val isFavorite: Boolean,
    val salary: Salary,
    val schedules: List<String>,
    val appliedNumber: Int,
    val description: String?,
    val responsibilities: String,
    val questions: List<String>
): VacancyItem {
    override fun id() = id

    override fun content(another: VacancyItem): Boolean =
        another is Vacancy
                && id == another.id
                && lookingNumber == another.lookingNumber
                && title == another.title
                && address == another.address
                && company == another.company
                && experience == another.experience
                && publishedDate == another.publishedDate
                && isFavorite == another.isFavorite
                && salary == another.salary
                && schedules == another.schedules
                && appliedNumber == another.appliedNumber
                && description == another.description
                && responsibilities == another.responsibilities
                && questions == another.questions

}