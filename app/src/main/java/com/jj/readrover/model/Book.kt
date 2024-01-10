package com.jj.readrover.model

data class Book(
    val items: List<Item>,
    val kind: String,
    val totalItems: Int
)