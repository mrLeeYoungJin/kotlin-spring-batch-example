package com.lyjguy.kotlinspringbatchexample.example.part6_1_4_2

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JpaCursorItemReader
import org.springframework.batch.item.database.builder.JpaCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManagerFactory

@Configuration
class JpaCursorConfiguration {
    private lateinit var jobBuilderFactory: JobBuilderFactory
    private lateinit var stepBuilderFactory: StepBuilderFactory
    private lateinit var entityManagerFactory: EntityManagerFactory

    @Bean
    fun job(): Job {
        return jobBuilderFactory["batchJob"]
            .incrementer(RunIdIncrementer())
            .start(step1())
            .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory["step1"]
            .chunk<Customer, Customer>(2)
            .reader(customItemReader())
            .writer(customItemWriter())
            .build()
    }

    @Bean
    fun customItemReader(): JpaCursorItemReader<Customer> {
        val parameters = HashMap<String, Any>()
        parameters["firstname"] = "A%"
        return JpaCursorItemReaderBuilder<Customer>()
            .name("jpaCursorItemReader")
            .queryString("select c from Customer c where firstname like :firstname")
            .entityManagerFactory(entityManagerFactory)
            .parameterValues(parameters) //                .maxItemCount(10)
            //                .currentItemCount(2)
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