package com.lyjguy.kotlinspringbatchexample.example.part4_2_2_1

import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SimpleJobConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun batchJob(): Job {
        return jobBuilderFactory!!["batchJob"]
            .incrementer(RunIdIncrementer())
            .validator { }
            .preventRestart()
            .start(step1())
            .next(step2())
            .next(step3())
            .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory!!["step1"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println("step1 has executed")
                RepeatStatus.FINISHED
            }
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
    fun step3(): Step {
        return stepBuilderFactory!!["step3"]
            .tasklet { contribution: StepContribution, chunkContext: ChunkContext ->
//                    throw new RuntimeException("failed");
                chunkContext.stepContext.stepExecution.status = BatchStatus.FAILED
                contribution.exitStatus = ExitStatus.STOPPED
                println("step3 has executed")
                RepeatStatus.FINISHED
            }
            .build()
    }
}