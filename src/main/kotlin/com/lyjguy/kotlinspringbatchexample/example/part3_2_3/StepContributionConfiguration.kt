package com.lyjguy.kotlinspringbatchexample.example.part3_2_3

import org.springframework.batch.core.ExitStatus
import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class StepContributionConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun BatchJob(): Job {
        return jobBuilderFactory!!["Job"]
            .start(step1())
            .next(step2())
            .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory!!["step1"]
            .tasklet { contribution, chunkContext ->
                println("contribution.getExitStatus(): " + contribution.exitStatus)
                println("contribution.getStepExecution().getStepName(): " + contribution.stepExecution.stepName)
                println("contribution.getStepExecution().getJobExecution().getJobInstance().getJobName(): " + contribution.stepExecution.jobExecution.jobInstance.jobName)
                contribution.exitStatus = ExitStatus.STOPPED
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun step2(): Step {
        return stepBuilderFactory!!["step2"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
//                    throw new RuntimeException("step has failed");
                println("step2 has executed")
                RepeatStatus.FINISHED
            }
            .build()
    }
}