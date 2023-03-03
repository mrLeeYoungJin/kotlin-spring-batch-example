package com.lyjguy.kotlinspringbatchexample.example.part6_1_4_4

import javax.persistence.*

@Entity
class Address(
    @Id
    @GeneratedValue
    val id: Long,
    val location: String,

    @OneToOne
    @JoinColumn(name = "customer_id")
    val customer: Customer,
)