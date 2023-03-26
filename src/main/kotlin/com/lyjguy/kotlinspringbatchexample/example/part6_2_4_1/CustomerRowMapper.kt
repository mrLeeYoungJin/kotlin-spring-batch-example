package com.lyjguy.kotlinspringbatchexample.example.part6_2_4_1

import org.springframework.jdbc.core.RowMapper
import java.sql.ResultSet
import java.sql.SQLException

class CustomerRowMapper : RowMapper<Customer> {

    @Throws(SQLException::class)
    override fun mapRow(rs: ResultSet, i: Int): Customer {
        return Customer(
            rs.getLong("id"),
            rs.getString("firstName"),
            rs.getString("lastName"),
            rs.getDate("birthdate")
        )
    }
}