package com.lyjguy.kotlinspringbatchexample.example.part5_4

import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.ItemStreamException
import org.springframework.batch.item.ItemStreamWriter

class CustomItemWriter : ItemStreamWriter<String> {
    @Throws(Exception::class)
    override fun write(items: List<String>) {
        items.forEach { item: String? -> println(item) }
    }

    @Throws(ItemStreamException::class)
    override fun open(executionContext: ExecutionContext) {
        println("")
    }

    @Throws(ItemStreamException::class)
    override fun update(executionContext: ExecutionContext) {
        println("")
    }

    @Throws(ItemStreamException::class)
    override fun close() {
        println("")
    }
}
