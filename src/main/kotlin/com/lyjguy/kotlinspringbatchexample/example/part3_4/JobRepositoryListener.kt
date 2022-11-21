package com.lyjguy.kotlinspringbatchexample.example.part3_4

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.repository.JobRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component


@Component
class JobRepositoryListener : JobExecutionListener {
    @Autowired
    private val jobRepository: JobRepository? = null
    override fun beforeJob(jobExecution: JobExecution) {}
    override fun afterJob(jobExecution: JobExecution) {
        val jobName = jobExecution.jobInstance.jobName
        val jobParameters = JobParametersBuilder().addString("requestDate", "20210102").toJobParameters()
        val lastExecution = jobRepository!!.getLastJobExecution(jobName, jobParameters)
        if (lastExecution !== null) {
            for (execution in lastExecution.stepExecutions) {
                val status = execution.status
                println("BatchStatus = " + status.isRunning)
                println("BatchStatus = " + status.name)
            }
        }
    }
}