package com.lyjguy.kotlinspringbatchexample.example.part5_5

import org.springframework.batch.item.ItemProcessor
import java.util.*

class CustomItemProcessor : ItemProcessor<Customer, Customer> {
    @Throws(Exception::class)
    override fun process(customer: Customer): Customer {
        return customer.copy(
            name = customer.name.uppercase(Locale.getDefault())
        )
    }
}