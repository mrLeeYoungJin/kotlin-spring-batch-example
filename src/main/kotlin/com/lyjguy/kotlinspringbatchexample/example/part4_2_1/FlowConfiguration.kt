//package com.lyjguy.kotlinspringbatchexample.example.part4_2_1
//
//import org.springframework.batch.core.Step
//import org.springframework.batch.core.StepContribution
//import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
//import org.springframework.batch.core.job.builder.FlowBuilder
//import org.springframework.batch.core.job.flow.Flow
//import org.springframework.batch.core.scope.context.ChunkContext
//import org.springframework.batch.repeat.RepeatStatus
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//
//@Configuration
//class FlowConfiguration {
//    private val stepBuilderFactory: StepBuilderFactory? = null
//    @Bean
//    fun step3(): Step {
//        return stepBuilderFactory!!["step3"]
//            .tasklet { contribution, chunkContext ->
//                println("step3 has executed")
//                RepeatStatus.FINISHED
//            }
//            .build()
//    }
//
//    @Bean
//    fun step4(): Step {
//        return stepBuilderFactory!!["step4"]
//            .tasklet { contribution: StepContribution?, chunkContext: ChunkContext? ->
//                println("step4 has executed")
//                RepeatStatus.FINISHED
//            }
//            .build()
//    }
//
//    @Bean
//    fun flow(): Flow {
//        val flowBuilder = FlowBuilder<Flow>("flow")
//        flowBuilder.start(step3())
//            .next(step4())
//            .end()
//        return flowBuilder.build()
//    }
//}