package com.lyjguy.kotlinspringbatchexample.example.part4_2_5

import org.springframework.batch.core.*
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.step.job.DefaultJobParametersExtractor
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class JobStepConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun parentJob(): Job {
        return jobBuilderFactory!!["parentJob"]
            .start(jobStep(null))
            .next(step2())
            .build()
    }

    @Bean
    fun jobStep(jobLauncher: JobLauncher?): Step {
        return stepBuilderFactory!!["jobStep"]
            .job(childJob())
            .launcher(jobLauncher!!)
            .listener(object : StepExecutionListener {
                override fun beforeStep(stepExecution: StepExecution) {
                    stepExecution.executionContext.putString("name", "user1")
                }

                override fun afterStep(stepExecution: StepExecution): ExitStatus? {
                    return null
                }
            })
            .parametersExtractor(jobParametersExtractor())
            .build()
    }

    @Bean
    fun childJob(): Job {
        return jobBuilderFactory!!["childJob"]
            .start(step1())
            .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory!!["step1"]
            .tasklet { contribution, chunkContext -> //                        throw new RuntimeException("step1 was failed");
                RepeatStatus.FINISHED
            }
            .build()
    }

    @Bean
    fun step2(): Step {
        return stepBuilderFactory!!["step2"]
            .tasklet { contribution, chunkContext ->
                throw RuntimeException("step2 was failed")
                //                        return RepeatStatus.FINISHED;
            }
            .build()
    }

    @Bean
    fun jobParametersExtractor(): DefaultJobParametersExtractor {
        val extractor = DefaultJobParametersExtractor()
        extractor.setKeys(arrayOf("name"))
        return extractor
    }
}