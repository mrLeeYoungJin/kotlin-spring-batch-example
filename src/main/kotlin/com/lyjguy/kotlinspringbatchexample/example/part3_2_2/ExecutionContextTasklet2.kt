package com.lyjguy.kotlinspringbatchexample.example.part3_2_2

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component


@Component
class ExecutionContextTasklet2 : Tasklet {
    @Throws(Exception::class)
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        val name = chunkContext.stepContext.stepExecution.jobExecution.executionContext["name"]
        if (name == null) {
            chunkContext.stepContext.stepExecution.jobExecution.executionContext.put("name", "user1")
            println("ExecutionContextTasklet2 has executed")
            throw RuntimeException("step has failed")
        }
        return RepeatStatus.FINISHED
    }
}