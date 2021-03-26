package com.example.okta.testslices.repository

import com.example.okta.testslices.entity.StudentScore
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.*
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = ANY)
class StudentScoreRepositoryTest {
    @Autowired
    private lateinit var studentScoreRepository: StudentScoreRepository

    @Test
    fun `insert two scores and find all`() {
        // given
        studentScoreRepository.saveAll(
            listOf(
                StudentScore(0, "Ann", 99),
                StudentScore(0, "Bob", 99)
            )
        )
        studentScoreRepository.flush()

        // when
        val students = studentScoreRepository.findAll()

        // then
        assertThat(students)
            .hasSize(2)
            .extracting<String>(StudentScore::name)
            .containsOnly("Ann", "Bob")
    }

    @Test
    fun `find students by first two characters`() {
        // given
        studentScoreRepository.saveAll(
            listOf(
                StudentScore(0, "Pedro", 92),
                StudentScore(0, "Pearl", 42),
                StudentScore(0, "Ponyo", 93)
            )
        )
        studentScoreRepository.flush()

        // when
        val students = studentScoreRepository.findStudentScoresByScoreGreaterThan(90)

        // then
        assertThat(students)
            .hasSize(2)
            .extracting<String>(StudentScore::name)
            .containsOnly("Pedro", "Ponyo")
    }
}
