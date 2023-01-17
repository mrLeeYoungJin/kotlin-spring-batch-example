package com.lyjguy.kotlinspringbatchexample.example.part5_3

import org.springframework.batch.item.ItemProcessor

class CustomItemProcessor : ItemProcessor<Customer, Customer> {
    @Throws(Exception::class)
    override fun process(customer: Customer): Customer {
        return customer.copy(
            name = customer.name.uppercase()
        )
    }
}