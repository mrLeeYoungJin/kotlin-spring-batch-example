package com.lyjguy.kotlinspringbatchexample.example.part6_1_1_1

import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet

class CustomerFieldSetMapper : FieldSetMapper<Customer> {
    override fun mapFieldSet(fs: FieldSet): Customer {
        return Customer(
            name = fs.readString(0),
            age = fs.readInt(1),
            year = fs.readString(2)
        )
    }
}