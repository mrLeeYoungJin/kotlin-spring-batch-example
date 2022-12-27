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
class TransitionConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun batchJob(): Job {
        return jobBuilderFactory!!["batchJob"]
            .start(step1())
            .on("FAILED")
            .to(step2())
            .on("FAILED")
            .stop()
            .from(step1())
            .on("*")
            .to(step3())
            .next(step4())
            .from(step2())
            .on("*")
            .to(step5())
            .end()
            .build()
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
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println(">> step2 has executed")
                RepeatStatus.FINISHED
            } //                .listener(new PassCheckingListener())
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

    internal class PassCheckingListener : StepExecutionListenerSupport() {
        override fun afterStep(stepExecution: StepExecution): ExitStatus? {
            val exitCode = stepExecution.exitStatus.exitCode
            return if (exitCode != ExitStatus.FAILED.exitCode) {
                ExitStatus("DO PASS ")
            } else {
                null
            }
        }
    }
}