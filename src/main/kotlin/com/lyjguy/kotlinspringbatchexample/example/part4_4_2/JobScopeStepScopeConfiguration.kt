package com.lyjguy.kotlinspringbatchexample.example.part4_4_2

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepScope
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JobScopeStepScopeConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun job(): Job {
        return jobBuilderFactory!!["batchJob"]
            .start(step1(null))
            .next(step2())
            .listener(JobListener())
            .build()
    }

    @Bean
    @JobScope
    fun step1(@Value("#{jobParameters['message']}") message: String?): Step {
        println("jobParameters['message'] : $message")
        return stepBuilderFactory!!["step1"]
            .tasklet(tasklet1(null))
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
    @StepScope
    fun tasklet1(@Value("#{jobExecutionContext['name']}") name: String?): Tasklet {
        return Tasklet { stepContribution: StepContribution?, chunkContext: ChunkContext? ->
            println("jobExecutionContext['name'] : $name")
            RepeatStatus.FINISHED
        }
    }
}