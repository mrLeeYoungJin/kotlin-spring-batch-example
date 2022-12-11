package com.lyjguy.kotlinspringbatchexample.example.part4_2_4_2

import org.springframework.batch.core.StepContribution
import org.springframework.batch.core.scope.context.ChunkContext
import org.springframework.batch.core.step.tasklet.Tasklet
import org.springframework.batch.repeat.RepeatStatus
import org.springframework.stereotype.Component

@Component
class CustomTasklet : Tasklet {
    @Throws(Exception::class)
    override fun execute(stepContribution: StepContribution, chunkContext: ChunkContext): RepeatStatus? {
        println("stepContribution = $stepContribution, chunkContext = $chunkContext")
        return RepeatStatus.FINISHED
    }
}