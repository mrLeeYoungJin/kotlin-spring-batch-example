package com.lyjguy.kotlinspringbatchexample.example.part6_1_1_4

import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.FieldSet

class CustomerFieldSetMapper : FieldSetMapper<Customer> {
    override fun mapFieldSet(fieldSet: FieldSet): Customer {
        return Customer(
            name = fieldSet.readString(0),
            year = fieldSet.readString(1),
            age = fieldSet.readInt(2)
        )
    }
}