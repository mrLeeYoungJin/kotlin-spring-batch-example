package com.lyjguy.kotlinspringbatchexample.example.part4_3_1_3_3

/*
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
class BatchStatusExitStatusConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null

*/
/*    @Bean
    fun batchJob(): Job {
        return jobBuilderFactory!!["batchJob"]
            .start(step1())
            .next(step2())
            .build()
    }*//*


    @Bean
    fun batchJob(): Job {
        return jobBuilderFactory!!["batchJob"]
            .start(step1())
            .on("FAILED")
            .to(step2())
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
            .build()
    }
}
*/
