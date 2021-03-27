package com.example.okta.testslices.entity

import org.hibernate.annotations.CreationTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(
    indexes = [
        Index(columnList = "score"),
        Index(columnList = "name")
    ]
)
data class StudentScore(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,

    @Column
    val name: String,

    @Column
    val score: Int,

    @CreationTimestamp
    @Column(updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now()
)
