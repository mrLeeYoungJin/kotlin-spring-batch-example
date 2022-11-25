package com.lyjguy.kotlinspringbatchexample.example.part4_1

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JobConfiguration2 {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun batchJob2(): Job {
        return jobBuilderFactory!!["batchJob2"]
            .incrementer(RunIdIncrementer())
            .start(step3())
            .next(step4())
            .build()
    }

    @Bean
    fun step3(): Step {
        return stepBuilderFactory!!["step3"]
            .tasklet { contribution, chunkContext ->
                println("step3 has executed")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun step4(): Step {
        return stepBuilderFactory!!["step4"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println("step4 has executed")
                RepeatStatus.FINISHED
            }
            .build()
    }
}