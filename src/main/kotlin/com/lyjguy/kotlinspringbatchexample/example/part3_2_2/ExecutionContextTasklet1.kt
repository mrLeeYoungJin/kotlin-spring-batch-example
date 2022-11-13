package com.lyjguy.kotlinspringbatchexample.example.part3_2_2

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component


@Component
class ExecutionContextTasklet1 : Tasklet {
    @Throws(Exception::class)
    override fun execute(contribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        println("ExecutionContextTasklet1 has executed")
        return RepeatStatus.FINISHED
    }
}