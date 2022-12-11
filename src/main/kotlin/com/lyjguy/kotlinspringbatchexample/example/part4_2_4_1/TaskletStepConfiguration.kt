package com.lyjguy.kotlinspringbatchexample.example.part4_2_4_1

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.item.ItemProcessor
import org.springframework.batch.item.support.ListItemReader
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.util.*
import java.util.function.Consumer

@Configuration
class TaskletStepConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun batchJob(): Job {
        return jobBuilderFactory!!["batchJob"]
            .start(taskStep())
            .next(chunkStep())
            .build()
    }

    @Bean
    fun taskStep(): Step {
        return stepBuilderFactory!!["taskStep"]
            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
                println("step1 has executed")
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun chunkStep(): Step {
        return stepBuilderFactory!!["chunkStep"]
            .chunk<String?, String>(3)
            .reader(ListItemReader(listOf("item1", "item2", "item3")))
            .processor(ItemProcessor { item -> item.uppercase(Locale.getDefault()) })
            .writer { list: List<*> ->
                list.forEach(Consumer { item: Any? ->
                    println(
                        item
                    )
                })
            }
            .build()
    }
}