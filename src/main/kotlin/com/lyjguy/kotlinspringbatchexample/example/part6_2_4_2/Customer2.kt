package com.lyjguy.kotlinspringbatchexample.example.part6_2_4_2

import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Customer2(
    @Id
    val id: Long = 0,
    val firstName: String,
    val lastName: String,
    val birthdate: String,
)