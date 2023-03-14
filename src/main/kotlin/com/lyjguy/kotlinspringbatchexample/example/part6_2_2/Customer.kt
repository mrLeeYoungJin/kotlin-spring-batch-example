package com.lyjguy.kotlinspringbatchexample.example.part6_2_2

import java.util.*

data class Customer(
    val id: Long = 0,
    val firstName: String,
    val lastName: String,
    val birthdate: Date,
)