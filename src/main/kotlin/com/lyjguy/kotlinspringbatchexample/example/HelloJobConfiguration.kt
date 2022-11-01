package com.lyjguy.kotlinspringbatchexample.example

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class HelloJobConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {

    @Bean
    fun helloJob(): Job {
        return jobBuilderFactory.get("helloJob")
            .start(helloStep1())
            .next(helloStep2())
            .build()
    }

    @Bean
    fun helloStep1(): Step {
        return stepBuilderFactory.get("helloStep1")
            .tasklet { _, _ ->
                println(" ============================")
                println(" >> Hello Spring Batch")
                println(" ============================")
                RepeatStatus.FINISHED
            }
            .build()
    }

    fun helloStep2(): Step {
        return stepBuilderFactory.get("helloStep2")
            .tasklet { _, _ ->
                println(" ============================")
                println(" >> Step2 has executed")
                println(" ============================")
                RepeatStatus.FINISHED
            }
            .build()
    }
}