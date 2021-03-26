package com.example.okta.testslices.entity

import javax.persistence.*

@Entity
@Table(
    indexes = [
        Index(columnList = "name", unique = false)
    ]
)
data class StudentScore(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,

    @Column
    val name: String,

    @Column
    val score: Int
)
