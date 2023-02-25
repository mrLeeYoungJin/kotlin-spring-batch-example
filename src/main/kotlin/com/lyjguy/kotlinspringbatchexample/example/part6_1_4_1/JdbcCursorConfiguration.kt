package com.lyjguy.kotlinspringbatchexample.example.part6_1_4_1

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JdbcCursorItemReader
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class JdbcCursorConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    private val dataSource: DataSource? = null
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
            .chunk<Customer, Customer>(10)
            .reader(customItemReader())
            .writer(customItemWriter())
            .build()
    }

    @Bean
    fun customItemReader(): JdbcCursorItemReader<Customer> {
        return JdbcCursorItemReaderBuilder<Customer>()
            .name("jdbcCursorItemReader")
            .fetchSize(10)
            .sql("select id, firstName, lastName, birthdate from customer where firstName like ? order by lastName, firstName")
            .beanRowMapper(Customer::class.java)
            .queryArguments("A%")
            .maxItemCount(3)
            .currentItemCount(2)
            .maxRows(100)
            .dataSource(dataSource!!)
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