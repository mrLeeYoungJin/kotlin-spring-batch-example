package com.lyjguy.kotlinspringbatchexample.example.part5_4

import org.springframework.batch.item.ExecutionContext
import org.springframework.batch.item.ItemStreamException
import org.springframework.batch.item.ItemStreamReader

class CustomItemStreamReader(private val items: List<String>) : ItemStreamReader<String?> {
    private var index = -1
    private var restart = false

    init {
        index = 0
    }

    @Throws(Exception::class)
    override fun read(): String? {
        var item: String? = null
        if (index < items.size) {
            item = items[index]
            index++
        }
        if (index == 6 && !restart) {
            throw RuntimeException("Restart is required.")
        }
        return item
    }

    @Throws(ItemStreamException::class)
    override fun open(executionContext: ExecutionContext) {
        if (executionContext.containsKey("index")) {
            index = executionContext.getInt("index")
            restart = true
        } else {
            index = 0
            executionContext.put("index", index)
        }
    }

    @Throws(ItemStreamException::class)
    override fun update(executionContext: ExecutionContext) {
        executionContext.put("index", index)
    }

    @Throws(ItemStreamException::class)
    override fun close() {
        println("close")
    }
}