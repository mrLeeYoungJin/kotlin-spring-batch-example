package com.lyjguy.kotlinspringbatchexample.example.part6_1_1_1

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.file.FlatFileItemReader
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource

@Configuration
class FlatFilesConfiguration {
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
            .chunk<Customer, Customer>(5)
            .reader(itemReader())
            .writer { items -> println("items = $items") }
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

    @Bean
    fun itemReader(): ItemReader<Customer> {
        val itemReader = FlatFileItemReader<Customer>()
        itemReader.setResource(ClassPathResource("/customer.csv"))
        val lineMapper = DefaultLineMapper<Customer>()
        lineMapper.setLineTokenizer(DelimitedLineTokenizer())
        lineMapper.setFieldSetMapper(CustomerFieldSetMapper())
        itemReader.setLineMapper(lineMapper)
        itemReader.setLinesToSkip(1)
        return itemReader
    }
}