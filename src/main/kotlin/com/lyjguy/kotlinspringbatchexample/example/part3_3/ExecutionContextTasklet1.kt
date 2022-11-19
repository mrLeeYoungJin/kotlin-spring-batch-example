package com.lyjguy.kotlinspringbatchexample.example.part3_3

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class ExecutionContextTasklet1 : Tasklet {
    @Throws(Exception::class)
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val jobExecutionContext = chunkContext.stepContext.stepExecution.jobExecution.executionContext
        val stepExecutionContext = chunkContext.stepContext.stepExecution.executionContext
        val jobName = chunkContext.stepContext.stepExecution.jobExecution.jobInstance.jobName
        val stepName = chunkContext.stepContext.stepExecution.stepName
        if (jobExecutionContext["jobName"] == null) {
            jobExecutionContext.put("jobName", jobName)
        }
        if (stepExecutionContext["stepName"] == null) {
            stepExecutionContext.put("stepName", stepName)
        }
        println("jobName: " + jobExecutionContext["jobName"])
        println("stepName: " + stepExecutionContext["stepName"])
        return RepeatStatus.FINISHED
    }
}