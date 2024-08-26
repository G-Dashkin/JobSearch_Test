package com.example.jobsearch_test.data.mappers

import com.example.jobsearch_test.models.Vacancy

internal fun Vacancy.toDomain(isFavorite:Boolean = false): Vacancy {
    return Vacancy(
        id = id,
        lookingNumber = lookingNumber,
        title = title,
        address = address,
        company = company,
        experience = experience,
        publishedDate = publishedDate,
        isFavorite = isFavorite,
        salary = salary,
        schedules = schedules,
        appliedNumber = appliedNumber,
        description = description,
        responsibilities =  responsibilities,
        questions = questions
    )
}