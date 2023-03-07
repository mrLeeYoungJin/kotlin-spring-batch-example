package com.lyjguy.kotlinspringbatchexample.example.part6_1_4_5

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.adapter.ItemReaderAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ItemReaderAdapterConfiguration {
    private lateinit var jobBuilderFactory: JobBuilderFactory
    private lateinit var stepBuilderFactory: StepBuilderFactory

    @Bean
    @Throws(Exception::class)
    fun job(): Job {
        return jobBuilderFactory["batchJob"]
            .incrementer(RunIdIncrementer())
            .start(step1())
            .build()
    }

    @Bean
    @Throws(Exception::class)
    fun step1(): Step {
        return stepBuilderFactory["step1"]
            .chunk<String, String>(10)
            .reader(customItemReader())
            .writer(customItemWriter())
            .build()
    }

    @Bean
    fun customItemReader(): ItemReaderAdapter<String> {
        val reader: ItemReaderAdapter<String> = ItemReaderAdapter<String>()
        reader.setTargetObject(customService())
        reader.setTargetMethod("joinCustomer")
        return reader
    }

    private fun customService(): CustomService {
        return CustomService()
    }

    @Bean
    fun customItemWriter(): ItemWriter<String> {
        return ItemWriter { items: List<String>? ->
            println(
                items
            )
        }
    }
}