package com.lyjguy.kotlinspringbatchexample.example.part4_3_2_2

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
class SimpleFlowConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun job(): Job {
        return jobBuilderFactory!!["batchJob"]
            .start(flow1())
            .on("COMPLETED")
            .to(flow2())
            .from(flow1())
            .on("FAILED")
            .to(flow3())
            .end()
            .build()
    }

    @Bean
    fun flow1(): Flow {
        val flowBuilder = FlowBuilder<Flow>("flow1")
        flowBuilder.start(step1())
            .next(step2())
            .end()
        return flowBuilder.build()
    }

    @Bean
    fun flow2(): Flow {
        val flowBuilder = FlowBuilder<Flow>("flow2")
        flowBuilder.start(flow3())
            .next(step5())
            .next(step6())
            .end()
        return flowBuilder.build()
    }

    @Bean
    fun flow3(): Flow {
        val flowBuilder = FlowBuilder<Flow>("flow3")
        flowBuilder.start(step3())
            .next(step4())
            .end()
        return flowBuilder.build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory!!["step1"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
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
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println(">> step3 has executed")
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    fun step4(): Step {
        return stepBuilderFactory!!["step4"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println(">> step4 has executed")
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    fun step5(): Step {
        return stepBuilderFactory!!["step5"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println(">> step5 has executed")
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    fun step6(): Step {
        return stepBuilderFactory!!["step6"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println(">> step6 has executed")
                RepeatStatus.FINISHED
            }.build()
    }
}