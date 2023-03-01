package com.lyjguy.kotlinspringbatchexample.example.part6_1_4_3

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

class CustomerRowMapper : RowMapper<Customer> {
    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, i: Int): Customer {
        return Customer(
            id = rs.getLong("id"),
            firstName = rs.getString("firstname"),
            lastName = rs.getString("lastname"),
            birthdate = rs.getString("birthdate")
        )
    }
}