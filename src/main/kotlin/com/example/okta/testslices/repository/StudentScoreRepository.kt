package com.example.okta.testslices.repository

import com.example.okta.testslices.entity.StudentScore
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StudentScoreRepository : JpaRepository<StudentScore, Long> {
    fun findStudentScoresByScoreGreaterThan(score: Int): List<StudentScore>
}
