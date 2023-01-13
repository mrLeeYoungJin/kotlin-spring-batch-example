package com.lyjguy.kotlinspringbatchexample.example.part5_2

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.JobScope
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.support.ListItemReader
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*

@Configuration
class ChunkOrientedTaskletConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun job(): Job {
        return jobBuilderFactory!!["batchJob"]
            .start(step1())
            .next(step2())
            .build()
    }

    @Bean
    @JobScope
    fun step1(): Step {
        return stepBuilderFactory!!["step1"]
            .chunk<String, String>(3)
            .reader(ListItemReader(Arrays.asList("item1", "item2", "item3", "item4", "item5", "item6")))
            .processor(ItemProcessor { item -> "my_$item" })
            .writer { items -> items.forEach { item: String? -> println(item) } }
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
}