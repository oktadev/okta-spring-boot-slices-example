package com.example.okta.testslices.entity

import javax.persistence.*

@Entity
@Table(
    indexes = [
        Index(columnList = "name", unique = true)
    ]
)
data class Country(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,

    @Column
    val name: String,

    @Column
    val population: Int
)
