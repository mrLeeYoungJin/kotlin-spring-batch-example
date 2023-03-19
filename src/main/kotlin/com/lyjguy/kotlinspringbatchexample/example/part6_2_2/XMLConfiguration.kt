package com.lyjguy.kotlinspringbatchexample.example.part6_2_2

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.database.JdbcPagingItemReader
import org.springframework.batch.item.database.Order
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider
import org.springframework.batch.item.xml.StaxEventItemWriter
import org.springframework.batch.item.xml.builder.StaxEventItemWriterBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import org.springframework.oxm.xstream.XStreamMarshaller
import java.util.*
import javax.sql.DataSource

@Configuration
class XMLConfiguration(
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
    fun customItemWriter(): StaxEventItemWriter<Customer> {
        return StaxEventItemWriterBuilder<Customer>()
            .name("customersWriter")
            .marshaller(itemMarshaller())
            .resource(FileSystemResource("customer.xml"))
            .rootTagName("customer")
            .overwriteOutput(true)
            .build()
    }

    @Bean
    fun itemMarshaller(): XStreamMarshaller {
        val aliases: MutableMap<String, Class<*>> = HashMap()
        aliases["customer"] = Customer::class.java
        aliases["id"] = Long::class.java
        aliases["firstName"] = String::class.java
        aliases["lastName"] = String::class.java
        aliases["birthdate"] = Date::class.java
        val xStreamMarshaller = XStreamMarshaller()
        xStreamMarshaller.setAliases(aliases)
        return xStreamMarshaller
    }
}