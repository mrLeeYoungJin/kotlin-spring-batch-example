package com.lyjguy.kotlinspringbatchexample.example.part4_3_1_3_3

import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.listener.StepExecutionListenerSupport
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CustomExitStatusConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun batchJob(): Job {
        return jobBuilderFactory!!["batchJob"]
            .start(step1())
            .on("FAILED")
            .to(step2())
            .on("PASS")
            .stop()
            .end()
            .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory!!["step1"]
            .tasklet { contribution: StepContribution, chunkContext: ChunkContext? ->
                println(">> step1 has executed")
                contribution.exitStatus = ExitStatus.FAILED
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun step2(): Step {
        return stepBuilderFactory!!["step2"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println(">> step2 has executed")
                RepeatStatus.FINISHED
            }
            .listener(PassCheckingListener())
            .build()
    }

    internal class PassCheckingListener : StepExecutionListenerSupport() {
        override fun afterStep(stepExecution: StepExecution): ExitStatus? {
            val exitCode = stepExecution.exitStatus.exitCode
            return if (exitCode != ExitStatus.FAILED.exitCode) {
                ExitStatus("PASS ")
            } else {
                null
            }
        }
    }
}