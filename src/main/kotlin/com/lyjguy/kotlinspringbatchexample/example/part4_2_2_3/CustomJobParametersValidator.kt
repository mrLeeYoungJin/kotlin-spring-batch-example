package com.lyjguy.kotlinspringbatchexample.example.part4_2_2_3

import org.springframework.batch.core.JobParameters
import org.springframework.batch.core.JobParametersInvalidException
import org.springframework.batch.core.JobParametersValidator

class CustomJobParametersValidator : JobParametersValidator {
    @Throws(JobParametersInvalidException::class)
    override fun validate(jobParameters: JobParameters?) {
        if (jobParameters!!.getString("name") == null) {
            throw JobParametersInvalidException("name parameter is not found.")
        }
    }
}
