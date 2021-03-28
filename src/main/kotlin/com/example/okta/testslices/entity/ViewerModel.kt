package com.example.okta.testslices.entity

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(
    indexes = [
        Index(columnList = "username", unique = true)
    ]
)
data class ViewerModel(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    val id: Long,

    @Column
    val username: String,

    @Column
    val visits: Int,

    @CreationTimestamp
    @Column(updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    @UpdateTimestamp
    @Column
    val updateAt: LocalDateTime = LocalDateTime.now()
)
