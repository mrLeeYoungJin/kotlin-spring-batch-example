package com.lyjguy.kotlinspringbatchexample.example.part6_1_2

import org.springframework.batch.core.Job
import org.springframework.batch.core.Step
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory
import org.springframework.batch.core.launch.support.RunIdIncrementer
import org.springframework.batch.item.ItemWriter
import org.springframework.batch.item.xml.StaxEventItemReader
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.ClassPathResource
import org.springframework.oxm.xstream.XStreamMarshaller

@Configuration
class XMLConfiguration {
    private val jobBuilderFactory: JobBuilderFactory? = null
    private val stepBuilderFactory: StepBuilderFactory? = null
    @Bean
    fun job(): Job {
        return jobBuilderFactory!!["batchJob"]
            .incrementer(RunIdIncrementer())
            .start(step1())
            .build()
    }

    @Bean
    fun step1(): Step {
        return stepBuilderFactory!!["step1"]
            .chunk<Customer, Customer>(3)
            .reader(customItemReader())
            .writer(customItemWriter())
            .build()
    }

    @Bean
    fun customItemReader(): StaxEventItemReader<Customer> {
        return StaxEventItemReaderBuilder<Customer>()
            .name("xmlFileItemReader")
            .resource(ClassPathResource("customer.xml"))
            .addFragmentRootElements("customer")
            .unmarshaller(itemMarshaller())
            .build()
    }

    /*@Bean
    public StaxEventItemReader<Customer> customItemReader() {
        XStreamMarshaller unmarshaller = new XStreamMarshaller();
        Map<String, Class> aliases = new HashMap<>();
        aliases.put("customer", Customer.class);
        aliases.put("id", Long.class);
        aliases.put("firstName", String.class);
        aliases.put("lastName", String.class);
        aliases.put("birthdate", Date.class);
        unmarshaller.setAliases(aliases);
        StaxEventItemReader<Customer> reader = new StaxEventItemReader<>();
        reader.setResource(new ClassPathResource("customers.xml"));
        reader.setFragmentRootElementName("customer");
        reader.setUnmarshaller(unmarshaller);
        return reader;
    }*/
    @Bean
    fun customItemWriter(): ItemWriter<Customer> {
        return ItemWriter { items: List<Customer> ->
            for (item in items) {
                println(item.toString())
            }
        }
    }

    @Bean
    fun itemMarshaller(): XStreamMarshaller {
        val aliases: MutableMap<String, Class<*>> = HashMap()
        aliases["customer"] = Customer::class.java
        aliases["id"] = Long::class.java
        aliases["name"] = String::class.java
        aliases["age"] = Int::class.java
        val xStreamMarshaller = XStreamMarshaller()
        xStreamMarshaller.setAliases(aliases)
        return xStreamMarshaller
    }
}