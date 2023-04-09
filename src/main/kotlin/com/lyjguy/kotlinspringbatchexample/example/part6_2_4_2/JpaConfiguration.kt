package com.lyjguy.kotlinspringbatchexample.example.part6_2_4_2

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.database.JdbcPagingItemReader
import org.springframework.batch.item.database.JpaItemWriter
import org.springframework.batch.item.database.Order
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder
import org.springframework.batch.item.database.support.MySqlPagingQueryProvider
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.persistence.EntityManagerFactory
import javax.sql.DataSource

@Configuration
class JpaConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
    private val dataSource: DataSource,
    private val entityManagerFactory: EntityManagerFactory,
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
            .chunk<Customer, Customer2>(10)
            .reader(customItemReader())
            .processor(customItemProcess())
            .writer(customItemWriter())
            .build()
    }

    @Bean
    fun customItemWriter(): JpaItemWriter<Customer2> {
        return JpaItemWriterBuilder<Customer2>()
            .entityManagerFactory(entityManagerFactory)
            .usePersist(true)
            .build()
    }

    @Bean
    fun customItemProcess(): ItemProcessor<in Customer, out Customer2> {
        return CustomItemProcess()
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
        parameters["firstname"] = "C%"
        reader.setParameterValues(parameters)
        return reader
    }
}