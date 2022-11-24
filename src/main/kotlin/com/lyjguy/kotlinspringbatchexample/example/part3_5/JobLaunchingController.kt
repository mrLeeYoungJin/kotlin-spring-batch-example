package com.lyjguy.kotlinspringbatchexample.example.part3_5

import org.springframework.batch.core.Job
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.launch.JobLauncher
import org.springframework.batch.core.launch.support.SimpleJobLauncher
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer
import org.springframework.core.task.SimpleAsyncTaskExecutor
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
class JobLaunchingController {
    @Autowired
    private val job: Job? = null

    @Autowired
    private val simpleLauncher: JobLauncher? = null

    @Qualifier("batchConfigurer")
    @Autowired
    private val basicBatchConfigurer: BasicBatchConfigurer? = null

    @PostMapping("/batch")
    @Throws(Exception::class)
    fun launch(@RequestBody member: Member): String {
        val jobParameters = JobParametersBuilder()
            .addString("id", member.id)
            .addDate("date", Date())
            .toJobParameters()

//		SimpleJobLauncher jobLauncher = (SimpleJobLauncher)simpleLauncher;
        val jobLauncher = basicBatchConfigurer!!.jobLauncher as SimpleJobLauncher
        jobLauncher.setTaskExecutor(SimpleAsyncTaskExecutor())
        jobLauncher.run(job!!, jobParameters)
        println("Job is completed")
        return "batch completed"
    }
}