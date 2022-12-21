package com.lyjguy.kotlinspringbatchexample.example.part4_3_1_2

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
class StartNextConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun batchJob(): Job {
        return jobBuilderFactory!!["batchJob"]
            .start(flowA())
            .next(step3())
            .next(flowB())
            .next(step6())
            .end()
            .build()
    }

    @Bean
    fun flowA(): Flow {
        val flowBuilder = FlowBuilder<Flow>("flowA")
        flowBuilder.start(step1())
            .next(step2())
            .end()
        return flowBuilder.build()
    }

    @Bean
    fun flowB(): Flow {
        val flowBuilder = FlowBuilder<Flow>("flowB")
        flowBuilder.start(step4())
            .next(step5())
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

    @Bean
    fun step4(): Step {
        return stepBuilderFactory!!["step4"]
            .tasklet { contribution, chunkContext ->
                println(">> step4 has executed")
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    fun step5(): Step {
        return stepBuilderFactory!!["step5"]
            .tasklet { contribution, chunkContext ->
                println(">> step5 has executed")
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    fun step6(): Step {
        return stepBuilderFactory!!["step6"]
            .tasklet { contribution, chunkContext ->
                println(">> step6 has executed")
                RepeatStatus.FINISHED
            }.build()
    }
}