package com.lyjguy.kotlinspringbatchexample.example.part3_1_3

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JobParameterConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {
    @Bean
    fun batchJob(): Job {
        return jobBuilderFactory.get("Job")
            .start(step1())
            .next(step2())
            .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory.get("step1")
            .tasklet { contribution, chunkContext ->
                val jobParameters = contribution.stepExecution.jobParameters
                val name = jobParameters.getString("name")
                val seq = jobParameters.getLong("seq")!!
                val date = jobParameters.getDate("date")
                println("===========================")
                println("name:$name")
                println("seq: $seq")
                println("date: $date")
                println("===========================")
                val jobParameters2 = chunkContext.stepContext.jobParameters
                val name2 = jobParameters2["name"] as String?
                val seq2 = jobParameters2["seq"] as Long
                println("step1 has executed")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun step2(): Step {
        return stepBuilderFactory.get("step2")
            .tasklet { contribution, chunkContext ->
                println("step1 has executed")
                RepeatStatus.FINISHED
            }
            .build()
    }
}