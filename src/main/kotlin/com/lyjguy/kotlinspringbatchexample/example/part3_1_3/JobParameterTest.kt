package com.lyjguy.kotlinspringbatchexample.example.part3_1_3

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import java.util.*

@Component
class JobParameterTest(
    private val jobLauncher: JobLauncher,
    private val job: Job,
) : ApplicationRunner {
    @Throws(Exception::class)
    override fun run(args: ApplicationArguments) {
        val jobParameters = JobParametersBuilder().addString("name", "user1")
            .addLong("seq", 1L)
            .addDate("date", Date())
            .toJobParameters()
        jobLauncher.run(job, jobParameters)
    }
}