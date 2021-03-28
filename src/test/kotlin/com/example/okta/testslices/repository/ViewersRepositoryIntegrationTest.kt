package com.example.okta.testslices.repository

import com.example.okta.testslices.entity.ViewerModel
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.*
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = ANY)
class ViewersRepositoryIntegrationTest {
    @Autowired
    private lateinit var viewersRepository: ViewersRepository

    @Test
    fun `insert two visitors and find all`() {
        // given
        viewersRepository.saveAll(
            listOf(
                ViewerModel(0, "Ann", 99),
                ViewerModel(0, "Bob", 99)
            )
        )
        viewersRepository.flush()

        // when
        val students = viewersRepository.findAll()

        // then
        assertThat(students)
            .hasSize(2)
            .extracting<String>(ViewerModel::username)
            .containsOnly("Ann", "Bob")
    }

    @Test
    fun `compute average amount of visits per user`() {
        // given
        viewersRepository.saveAll(
            listOf(
                ViewerModel(0, "Pedro", 200),
                ViewerModel(0, "Pearl", 100),
                ViewerModel(0, "Ponyo", 300)
            )
        )
        viewersRepository.flush()

        // when
        val viewers = viewersRepository.averageViewsPerUser()

        // then
        assertThat(viewers).isEqualTo(200.0)
    }

    @Test
    fun `incrementVisit adds single visit`() {
        // given
        viewersRepository.save(ViewerModel(0, "Maya", 1000))

        // when
        viewersRepository.incrementVisit("Maya")

        // then
        assertThat(viewersRepository.findByUsername("Maya"))
            .extracting<Int> { it?.visits }
            .isEqualTo(1001)
    }
}
