package com.lyjguy.kotlinspringbatchexample.example.part6_2_3

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.database.JdbcPagingItemReader
import org.springframework.batch.item.database.Order
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller
import org.springframework.batch.item.json.JsonFileItemWriter
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import javax.sql.DataSource

@Configuration
class JsonConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val dataSource: DataSource,
) {


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
            .chunk<Customer, Customer>(10)
            .reader(customItemReader())
            .writer(customItemWriter())
            .build()
    }

    @Bean
    fun customItemReader(): JdbcPagingItemReader<Customer> {
        val reader = JdbcPagingItemReader<Customer>()
        reader.setDataSource(dataSource)
        reader.setFetchSize(10)
        reader.setRowMapper(CustomerRowMapper())
        val queryProvider = MySqlPagingQueryProvider()
        queryProvider.setSelectClause("id, firstName, lastName, birthdate")
        queryProvider.setFromClause("from customer")
        queryProvider.setWhereClause("where firstname like :firstname")
        val sortKeys: MutableMap<String, Order> = HashMap(1)
        sortKeys["id"] = Order.ASCENDING
        queryProvider.sortKeys = sortKeys
        reader.setQueryProvider(queryProvider)
        val parameters = HashMap<String, Any>()
        parameters["firstname"] = "A%"
        reader.setParameterValues(parameters)
        return reader
    }

    @Bean
    fun customItemWriter(): JsonFileItemWriter<Customer> {
        return JsonFileItemWriterBuilder<Customer>()
            .jsonObjectMarshaller(JacksonJsonObjectMarshaller())
            .resource(ClassPathResource("customer.json"))
            .name("customerJsonFileItemWriter")
            .build()
    }
}