package com.lyjguy.kotlinspringbatchexample.example.part4_2_2_5

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersBuilder
import org.springframework.batch.core.JobParametersIncrementer
import java.text.SimpleDateFormat
import java.util.*

class CustomJobParametersIncrementer : JobParametersIncrementer {
    override fun getNext(parameters: JobParameters?): JobParameters {
        val id = format.format(Date())
        return JobParametersBuilder().addString("run.id", id).toJobParameters()
    }

    companion object {
        val format = SimpleDateFormat("yyyyMMdd-hhmmss")
    }
}
