package com.lyjguy.kotlinspringbatchexample.example.part6_1_1_1

import org.springframework.batch.item.file.LineMapper
import org.springframework.batch.item.file.mapping.FieldSetMapper
import org.springframework.batch.item.file.transform.LineTokenizer

class DefaultLineMapper<T> : LineMapper<T> {
    private var tokenizer: LineTokenizer? = null
    private var fieldSetMapper: FieldSetMapper<T>? = null

    @Throws(Exception::class)
    override fun mapLine(line: String, lineNumber: Int): T {
        return fieldSetMapper!!.mapFieldSet(tokenizer!!.tokenize(line))
    }

    fun setLineTokenizer(tokenizer: LineTokenizer?) {
        this.tokenizer = tokenizer
    }

    fun setFieldSetMapper(fieldSetMapper: FieldSetMapper<T>?) {
        this.fieldSetMapper = fieldSetMapper
    }
}