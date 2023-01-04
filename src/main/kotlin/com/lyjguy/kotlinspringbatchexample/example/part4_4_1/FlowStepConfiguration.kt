package com.lyjguy.kotlinspringbatchexample.example.part4_4_1

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.builder.FlowBuilder
import org.springframework.batch.core.job.flow.Flow
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FlowStepConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun job(): Job {
        return jobBuilderFactory!!["batchJob"]
            .start(flowStep())
            .next(step2())
            .build()
    }

    fun flowStep(): Step {
        return stepBuilderFactory!!["flowStep"]
            .flow(flow())
            .build()
    }

    @Bean
    fun flow(): Flow {
        val flowBuilder = FlowBuilder<Flow>("flow")
        flowBuilder.start(step1())
            .end()
        return flowBuilder.build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory!!["step1"]
            .tasklet { contribution, chunkContext ->
                println("step1 was executed")
                throw RuntimeException("step1 was failed")
                //                        return RepeatStatus.FINISHED;
            }
            .build()
    }

    @Bean
    fun step2(): Step {
        return stepBuilderFactory!!["step2"]
            .tasklet { contribution, chunkContext ->
                println("step2 was executed")
                RepeatStatus.FINISHED
            }
            .build()
    }
}