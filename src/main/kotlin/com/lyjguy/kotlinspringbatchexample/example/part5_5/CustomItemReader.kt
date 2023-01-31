package com.lyjguy.kotlinspringbatchexample.example.part5_5

import org.springframework.batch.item.ItemReader

class CustomItemReader(
    private val list: List<Customer>
) : ItemReader<Customer> {

    override fun read(): Customer? {
        return if (!list.isEmpty()) {
            list.get(0)
        } else null
    }
}