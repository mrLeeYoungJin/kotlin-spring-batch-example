//package com.lyjguy.kotlinspringbatchexample.example.part4_2_1
//
//import org.springframework.batch.core.Job
//import org.springframework.batch.core.Step
//import org.springframework.batch.core.StepContribution
//import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
//import org.springframework.batch.core.job.flow.Flow
//import org.springframework.batch.core.launch.support.RunIdIncrementer
//import org.springframework.batch.core.scope.context.ChunkContext
//import org.springframework.batch.repeat.RepeatStatus
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
//class JobBuilderConfiguration2 {
//    private val jobBuilderFactory: JobBuilderFactory? = null
//    private val stepBuilderFactory: StepBuilderFactory? = null
//    private val flow: Flow? = null
//    @Bean
//    fun batchJob2(): Job {
//        return jobBuilderFactory!!["batchJob2"]
//            .incrementer(RunIdIncrementer())
//            .start(flow!!)
//            .next(step5())
//            .end()
//            .build()
//    }
//
//    @Bean
//    fun step5(): Step {
//        return stepBuilderFactory!!["step5"]
//            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
//                println("step5 has executed")
//                RepeatStatus.FINISHED
//            }
//            .build()
//    }
//}
