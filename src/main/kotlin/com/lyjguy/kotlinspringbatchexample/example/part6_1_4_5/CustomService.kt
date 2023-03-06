package com.lyjguy.kotlinspringbatchexample.example.part6_1_4_5

class CustomService {
    private var cnt = 0
    fun joinCustomer(): String {
        return ("item" + cnt++)
    }
}