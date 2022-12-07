package com.lyjguy.kotlinspringbatchexample.example.part4_2_2_6

import org.springframework.batch.core.JobExecution
import org.springframework.batch.core.JobExecutionListener

class CustomJobListener : JobExecutionListener {
    override fun beforeJob(jobExecution: JobExecution) {
        println("jobExecution = $jobExecution")
    }

    override fun afterJob(jobExecution: JobExecution) {
        println("jobExecution = $jobExecution")
    }
}