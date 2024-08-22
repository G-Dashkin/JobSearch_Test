package com.example.utils

fun List<*>?.hasNoEmptyValues(): Boolean {
    return this!!.all { it != "" }
}