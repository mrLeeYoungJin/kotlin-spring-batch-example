package com.lyjguy.kotlinspringbatchexample.example.part6_1_1_3

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper
import org.springframework.batch.item.file.transform.Range
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource
import java.util.function.Consumer


@Configuration
class FlatFilesFixedLengthConfiguration {
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
            .resource(FileSystemResource("C:\\lecture\\src\\main\\resources\\customer.txt"))
            .fieldSetMapper(BeanWrapperFieldSetMapper())
            .targetType(Customer::class.java)
            .linesToSkip(1)
            .fixedLength()
            .addColumns(Range(1, 5))
            .addColumns(Range(6, 9))
            .addColumns(Range(10, 11)) /*.addColumns(new Range(1))
                .addColumns(new Range(6))
                .addColumns(new Range(10))*/
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