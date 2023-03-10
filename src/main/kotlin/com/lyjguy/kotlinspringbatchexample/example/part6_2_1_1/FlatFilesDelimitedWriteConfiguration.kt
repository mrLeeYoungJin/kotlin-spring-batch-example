package com.lyjguy.kotlinspringbatchexample.example.part6_2_1_1

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.file.FlatFileItemWriter
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder
import org.springframework.batch.item.support.ListItemReader
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.FileSystemResource

@Configuration
class FlatFilesDelimitedWriteConfiguration(
    private val jobBuilderFactory: JobBuilderFactory,
    private val stepBuilderFactory: StepBuilderFactory,
) {


    @Bean
    @Throws(Exception::class)
    fun job(): Job {
        return jobBuilderFactory["batchJob"]
            .incrementer(RunIdIncrementer())
            .start(step1())
            .build()
    }

    @Bean
    @Throws(Exception::class)
    fun step1(): Step {
        return stepBuilderFactory["step1"]
            .chunk<Customer, Customer>(1)
            .reader(customItemReader())
            .writer(customItemWriter())
            .build()
    }

    @Bean
    fun customItemReader(): ListItemReader<Customer> {
        return ListItemReader(listOf(
            Customer(1, "hong gil dong1", 41),
            Customer(2, "hong gil dong2", 42),
            Customer(3, "hong gil dong3", 43)
        ))
    }

    @Bean
    @Throws(Exception::class)
    fun customItemWriter(): FlatFileItemWriter<Customer> {
        return FlatFileItemWriterBuilder<Customer>()
            .name("customerWriter")
            .resource(FileSystemResource("C:\\jsw\\inflearn\\spring-batch-lecture\\src\\main\\resources\\customer.csv"))
            .append(true)
            .delimited()
            .delimiter(",")
            .names("id", "name", "age")
            .build()
    }

    /*@Bean
    public FlatFileItemWriter<Customer> customItemWriter() throws Exception {
        BeanWrapperFieldExtractor<Customer> fieldExtractor = new BeanWrapperFieldExtractor<>();
        fieldExtractor.setNames(new String[] {"id","name","age"});
        fieldExtractor.afterPropertiesSet();
        DelimitedLineAggregator<Customer> lineAggregator = new DelimitedLineAggregator<>();
        lineAggregator.setDelimiter(",");
        lineAggregator.setFieldExtractor(fieldExtractor);
        return new FlatFileItemWriterBuilder<Customer>()
                .name("CustomerWriter")
                .resource(new ClassPathResource("customer.csv"))
                .lineAggregator(lineAggregator)
                .build();
    }*/
}