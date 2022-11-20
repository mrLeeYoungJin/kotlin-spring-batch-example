package com.lyjguy.kotlinspringbatchexample.example.part3_3

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class ExecutionContextConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    private val executionContextTasklet1: ExecutionContextTasklet1? = null
    private val executionContextTasklet2: ExecutionContextTasklet2? = null
    private val executionContextTasklet3: ExecutionContextTasklet3? = null
    private val executionContextTasklet4: ExecutionContextTasklet4? = null
    @Bean
    fun BatchJob(): Job {
        return jobBuilderFactory!!["Job"]
            .start(step1())
            .next(step2())
            .next(step3())
            .next(step4())
            .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory!!["step1"]
            .tasklet(executionContextTasklet1!!)
            .build()
    }

    @Bean
    fun step2(): Step {
        return stepBuilderFactory!!["step2"]
            .tasklet(executionContextTasklet2!!)
            .build()
    }

    @Bean
    fun step3(): Step {
        return stepBuilderFactory!!["step3"]
            .tasklet(executionContextTasklet3!!)
            .build()
    }

    @Bean
    fun step4(): Step {
        return stepBuilderFactory!!["step4"]
            .tasklet(executionContextTasklet4!!)
            .build()
    }
}