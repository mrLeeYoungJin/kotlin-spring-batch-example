package com.lyjguy.kotlinspringbatchexample.example.part5_3

import org.springframework.batch.item.ItemWriter

class CustomItemWriter : ItemWriter<Customer> {
    @Throws(Exception::class)
    override fun write(items: List<Customer>) {
        items.forEach { item: Customer -> println(item) }
    }
}