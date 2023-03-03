package com.lyjguy.kotlinspringbatchexample.example.part6_1_4_4

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.OneToOne

@Entity
class Customer(
    @Id
    @GeneratedValue
    val id: Long,
    val username: String,
    val age: Int = 0,

    @OneToOne(mappedBy = "customer")
    val address: Address,
)