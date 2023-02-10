package com.lyjguy.kotlinspringbatchexample.example.part6_1_1_2

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import java.util.function.Consumer


@Configuration
class FlatFilesDelimitedConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun job(): Job {
        return jobBuilderFactory!!["batchJob"]
            .start(step1())
            .next(step2())
            .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory!!["step1"]
            .chunk<Customer, Customer>(3)
            .reader(itemReader())
            .writer { items -> items.forEach(Consumer { item: Any? -> println(item) }) }
            .build()
    }

    fun itemReader(): FlatFileItemReader<Customer> {
        return FlatFileItemReaderBuilder<Customer>()
            .name("flatFile")
            .resource(ClassPathResource("customer.csv"))
            .fieldSetMapper(CustomerFieldSetMapper()) //                .targetType(Customer.class)
            .linesToSkip(1)
            .delimited().delimiter(",")
            .names("name", "year", "age")
            .build()
    }

    fun itemReader2(): FlatFileItemReader<*> {
        return FlatFileItemReaderBuilder<Customer>()
            .name("flatFile")
            .resource(ClassPathResource("customer.csv"))
            .fieldSetMapper(BeanWrapperFieldSetMapper())
            .targetType(Customer::class.java)
            .linesToSkip(1)
            .delimited().delimiter(",")
            .names("name", "year", "age")
            .build()
    }

    @Bean
    fun step2(): Step {
        return stepBuilderFactory!!["step2"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println("step2 has executed")
                RepeatStatus.FINISHED
            }
            .build()
    }
}