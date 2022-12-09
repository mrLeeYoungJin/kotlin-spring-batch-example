package com.lyjguy.kotlinspringbatchexample.example.part4_2_3

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.job.builder.FlowBuilder
import org.springframework.batch.core.job.flow.Flow
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class StepBuilderConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun batchJob(): Job {
        return jobBuilderFactory!!["batchJob"]
            .incrementer(RunIdIncrementer())
            .start(step1())
            .next(step2())
            .next(step4())
            .next(step5()) //                .next(step3())
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
            .chunk<String, String>(3)
            .reader { null }
            .writer { list: List<String>? -> }
            .build()
    }

    /* @Bean
    public Step step3() {
        return stepBuilderFactory.get("step3")
                .partitioner(step1())
                .gridSize(2)
                .build();
    }
*/
    @Bean
    fun step4(): Step {
        return stepBuilderFactory!!["step4"]
            .job(job())
            .build()
    }

    @Bean
    fun step5(): Step {
        return stepBuilderFactory!!["step5"]
            .flow(flow())
            .build()
    }

    @Bean
    fun job(): Job {
        return jobBuilderFactory!!["job"]
            .start(step1())
            .next(step2()) //                .next(step3())
            .build()
    }

    @Bean
    fun flow(): Flow {
        val flowBuilder = FlowBuilder<Flow>("flow")
        flowBuilder.start(step2()).end()
        return flowBuilder.build()
    }
}