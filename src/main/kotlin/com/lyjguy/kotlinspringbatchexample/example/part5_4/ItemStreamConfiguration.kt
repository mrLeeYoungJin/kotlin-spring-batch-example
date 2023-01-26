package com.lyjguy.kotlinspringbatchexample.example.part5_4

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ItemStreamConfiguration {
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
    fun step1(): Step {
        return stepBuilderFactory!!["step1"]
            .chunk<String, String>(5)
            .reader(itemReader())
            .writer(itemWriter())
            .build()
    }

    @Bean
    fun itemReader(): CustomItemStreamReader {
        val items: MutableList<String> = ArrayList(10)
        for (i in 1..10) {
            items.add(i.toString())
        }
        return CustomItemStreamReader(items)
    }

    @Bean
    fun itemWriter(): ItemWriter<String> {
        return CustomItemWriter()
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