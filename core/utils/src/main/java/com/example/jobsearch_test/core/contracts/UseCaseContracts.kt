package com.example.jobsearch_test.core.contracts

interface UseCaseWithParams <R, P> {
    suspend fun execute(params: P) : R
}

interface UseCaseWithoutParams <R> {
    suspend fun execute() : R
}