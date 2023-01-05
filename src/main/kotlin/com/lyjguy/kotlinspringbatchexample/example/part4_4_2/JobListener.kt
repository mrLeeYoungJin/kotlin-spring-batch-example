package com.lyjguy.kotlinspringbatchexample.example.part4_4_2

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener

class JobListener : JobExecutionListener {
    override fun beforeJob(jobExecution: JobExecution) {
        jobExecution.executionContext.putString("name", "user1")
    }

    override fun afterJob(jobExecution: JobExecution) {}
}