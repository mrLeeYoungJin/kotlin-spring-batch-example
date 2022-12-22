package com.lyjguy.kotlinspringbatchexample.example.part4_3_1_3_1

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.builder.FlowBuilder
import org.springframework.batch.core.job.flow.Flow
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class TransitionConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun batchJob(): Job {
        return jobBuilderFactory!!["batchJob"]
            .start(flow())
            .next(step3())
            .end()
            .build()
    }

    @Bean
    fun flow(): Flow {
        val flowBuilder = FlowBuilder<Flow>("flowA")
        flowBuilder.start(step1())
            .next(step2())
            .end()
        return flowBuilder.build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory!!["step1"]
            .tasklet { contribution, chunkContext ->
                println(">> step1 has executed")
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    fun step2(): Step {
        return stepBuilderFactory!!["step2"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println(">> step2 has executed")
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    fun step3(): Step {
        return stepBuilderFactory!!["step3"]
            .tasklet { contribution, chunkContext ->
                println(">> step3 has executed")
                RepeatStatus.FINISHED
            }.build()
    }
}