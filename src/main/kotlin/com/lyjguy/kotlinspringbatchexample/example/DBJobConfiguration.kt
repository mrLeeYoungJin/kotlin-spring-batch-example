package com.lyjguy.kotlinspringbatchexample.example

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class DBJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    fun helloJob(): Job {
        return jobBuilderFactory.get("job")
            .start(step1())
            .next(step2())
            .next(step3())
            .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory.get("step1")
            .tasklet { contribution, chunkContext ->
                println("step1 has executed")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun step2(): Step {
        return stepBuilderFactory.get("step2")
            .tasklet { contribution, chunkContext ->
                println("step2 has executed")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun step3(): Step {
        return stepBuilderFactory.get("step3")
            .tasklet { contribution, chunkContext ->
                println("step3 has executed")
                RepeatStatus.FINISHED
            }
            .build()
    }
}