package com.lyjguy.kotlinspringbatchexample

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@EnableBatchProcessing
@SpringBootApplication
class KotlinSpringBatchExampleApplication

fun main(args: Array<String>) {
	runApplication<KotlinSpringBatchExampleApplication>(*args)
}
