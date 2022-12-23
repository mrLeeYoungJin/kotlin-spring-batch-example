package com.lyjguy.kotlinspringbatchexample.example.part4_3_1_3_2

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
            .start(step1())
            .on("FAILED")
            .to(step2())
            .on("*")
            .stop()
            .from(step1()).on("*")
            .to(step5())
            .next(step6())
            .on("COMPLETED")
            .end()
            .end()
            .build()
    }

    @Bean
    fun flow(): Flow {
        val flowBuilder = FlowBuilder<Flow>("flow")
        flowBuilder
            .start(step3())
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
            }
            .build()
    }

    @Bean
    fun step2(): Step {
        return stepBuilderFactory!!["step2"]
            .flow(flow())
            .build()
    }

    @Bean
    fun step3(): Step {
        return stepBuilderFactory!!["step3"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println(">> step3 has executed")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun step4(): Step {
        return stepBuilderFactory!!["step4"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println(">> step4 has executed")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun step5(): Step {
        return stepBuilderFactory!!["step5"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println(">> step5 has executed")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun step6(): Step {
        return stepBuilderFactory!!["step6"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println(">> step6 has executed")
                RepeatStatus.FINISHED
            }
            .build()
    }
}