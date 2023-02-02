package com.lyjguy.kotlinspringbatchexample.example.part5_5

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.ItemReader
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ChunkArchitectureConfiguration {
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
            .processor(itemProcessor())
            .writer(itemWriter())
            .build()
    }

    @Bean
    fun itemReader(): ItemReader<Customer> {
        return CustomItemReader(
            listOf(
                Customer("user1"),
                Customer("user2"),
                Customer("user3")
            )
        )
    }

    @Bean
    fun itemProcessor(): ItemProcessor<Customer, Customer> {
        return CustomItemProcessor()
    }

    @Bean
    fun itemWriter(): ItemWriter<Customer> {
        return CustomItemWriter()
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