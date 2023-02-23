package com.lyjguy.kotlinspringbatchexample.example.part6_1_3

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.json.JacksonJsonObjectReader
import org.springframework.batch.item.json.JsonItemReader
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource


@Configuration
class JsonConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun job(): Job {
        return jobBuilderFactory!!["batchJob"]
            .incrementer(RunIdIncrementer())
            .start(step1())
            .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory!!["step1"]
            .chunk<Customer, Customer>(3)
            .reader(customItemReader())
            .writer(customItemWriter())
            .build()
    }

    @Bean
    fun customItemReader(): JsonItemReader<Customer> {
        return JsonItemReaderBuilder<Customer>()
            .jsonObjectReader(JacksonJsonObjectReader(Customer::class.java))
            .resource(ClassPathResource("customer.json"))
            .name("jsonItemReader")
            .build()
    }

    @Bean
    fun customItemWriter(): ItemWriter<Customer> {
        return ItemWriter { items: List<Customer> ->
            for (item in items) {
                println(item.toString())
            }
        }
    }
}