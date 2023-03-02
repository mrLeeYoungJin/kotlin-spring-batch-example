package com.lyjguy.kotlinspringbatchexample.example.part6_1_4_3

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.database.JdbcPagingItemReader
import org.springframework.batch.item.database.Order
import org.springframework.batch.item.database.PagingQueryProvider
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.BeanPropertyRowMapper
import javax.sql.DataSource

@Configuration
class JdbcPagingConfiguration {
    private lateinit var jobBuilderFactory: JobBuilderFactory
    private lateinit var stepBuilderFactory: StepBuilderFactory
    lateinit var dataSource: DataSource

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
    @Throws(Exception::class)
    fun customItemReader(): JdbcPagingItemReader<Customer> {
        val parameters = HashMap<String, Any>()
        parameters["firstname"] = "A%"
        return JdbcPagingItemReaderBuilder<Customer>()
            .name("jdbcPagingItemReader")
            .pageSize(10)
            .fetchSize(10)
            .dataSource(dataSource)
            .rowMapper(BeanPropertyRowMapper(Customer::class.java))
            .queryProvider(createQueryProvider())
            .parameterValues(parameters)
            .build()
    }

    @Bean
    @Throws(Exception::class)
    fun createQueryProvider(): PagingQueryProvider {
        val queryProvider = SqlPagingQueryProviderFactoryBean()
        queryProvider.setDataSource(dataSource)
        queryProvider.setSelectClause("id,firstName,lastName,birthdate")
        queryProvider.setFromClause("from customer")
        queryProvider.setWhereClause("where firstName like :firstname")
        val sortKeys: MutableMap<String, Order> = HashMap(1)
        sortKeys["id"] = Order.ASCENDING
        queryProvider.setSortKeys(sortKeys)
        return queryProvider.getObject()
    }

    /* @Bean
    public JdbcPagingItemReader<Customer> customItemReader() {
        JdbcPagingItemReader<Customer> reader = new JdbcPagingItemReader<>();
        reader.setDataSource(this.dataSource);
        reader.setFetchSize(5);
        reader.setPageSize(5);
        reader.setSaveState(true);
        reader.setRowMapper(new CustomerRowMapper());
        HashMap<String, Object> parameters = new HashMap<>();
        parameters.put("firstname", "A%");
        reader.setParameterValues(parameters);
        MySqlPagingQueryProvider queryProvider = new MySqlPagingQueryProvider();
        queryProvider.setSelectClause("id, firstName, lastName, birthdate");
        queryProvider.setFromClause("from customer");
        queryProvider.setWhereClause("where firstname like :firstname");
        Map<String, Order> sortKeys = new HashMap<>(1);
        sortKeys.put("id", Order.ASCENDING);
        queryProvider.setSortKeys(sortKeys);
        reader.setQueryProvider(queryProvider);
        return reader;
    }*/
    @Bean
    fun customItemWriter(): ItemWriter<Customer> {
        return ItemWriter { items: List<Customer> ->
            for (item in items) {
                println(item.toString())
            }
        }
    }
}