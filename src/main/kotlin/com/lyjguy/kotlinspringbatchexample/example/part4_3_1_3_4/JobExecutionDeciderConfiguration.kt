package com.lyjguy.kotlinspringbatchexample.example.part4_3_1_3_4

import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.flow.FlowExecutionStatus
import org.springframework.batch.core.job.flow.JobExecutionDecider
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JobExecutionDeciderConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun job(): Job {
        return jobBuilderFactory!!["batchJob"]
            .start(startStep())
            .next(decider())
            .from(decider()).on("ODD").to(oddStep())
            .from(decider()).on("EVEN").to(evenStep())
            .end()
            .build()
    }

    @Bean
    fun startStep(): Step {
        return stepBuilderFactory!!["startStep"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println("This is the start tasklet")
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    fun evenStep(): Step {
        return stepBuilderFactory!!["evenStep"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println(">>EvenStep has executed")
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    fun oddStep(): Step {
        return stepBuilderFactory!!["oddStep"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println(">>OddStep has executed")
                RepeatStatus.FINISHED
            }.build()
    }

    @Bean
    fun decider(): JobExecutionDecider {
        return CustomDecider()
    }

    class CustomDecider : JobExecutionDecider {
        private var count = 0
        override fun decide(jobExecution: JobExecution, stepExecution: StepExecution?): FlowExecutionStatus {
            count++
            return if (count % 2 == 0) {
                FlowExecutionStatus("EVEN")
            } else {
                FlowExecutionStatus("ODD")
            }
        }
    }
}