package com.lyjguy.kotlinspringbatchexample.example.part6_2_4_2

import org.modelmapper.ModelMapper
import org.springframework.batch.item.ItemProcessor

class CustomItemProcess : ItemProcessor<Customer, Customer2> {
    private val modelMapper: ModelMapper = ModelMapper()

    @Throws(Exception::class)
    override fun process(item: Customer): Customer2 {
        return modelMapper.map(item, Customer2::class.java)
    }
}