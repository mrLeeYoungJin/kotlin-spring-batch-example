package com.lyjguy.kotlinspringbatchexample.example.part4_2_4_2

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TaskletConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    private val customTasklet: CustomTasklet? = null
    @Bean
    fun batchJob(): Job {
        return jobBuilderFactory!!["batchJob"]
            .start(step1())
            .next(step2())
            .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory!!["step1"]
            .tasklet { stepContribution, chunkContext ->
                println("stepContribution = $stepContribution, chunkContext = $chunkContext")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun step2(): Step {
        return stepBuilderFactory!!["step2"]
            .tasklet(customTasklet!!)
            .build()
    }
}