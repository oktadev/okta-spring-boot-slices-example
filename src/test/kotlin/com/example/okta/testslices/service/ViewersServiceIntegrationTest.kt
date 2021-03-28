package com.example.okta.testslices.service

import com.example.okta.testslices.entity.ViewerModel
import com.example.okta.testslices.repository.ViewersRepository
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.ANY
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType.ASSIGNABLE_TYPE

@DataJpaTest(
    includeFilters = [
        ComponentScan.Filter(ViewersService::class, type = ASSIGNABLE_TYPE)
    ]
)
@AutoConfigureTestDatabase(replace = ANY)
internal class ViewersServiceIntegrationTest {
    @Autowired
    private lateinit var viewersService: ViewersService

    @Autowired
    private lateinit var viewersRepository: ViewersRepository

    @Test
    fun `insertOrIncrementViews creates a new visitor record when need`() {
        // when
        viewersService.insertOrIncrementViews("Jafari")

        // then
        assertThat(viewersService.allViewers())
            .hasSize(1)
            .first()
            .satisfies {
                assertThat(it.username).isEqualTo("Jafari")
                assertThat(it.visits).isEqualTo(1)
            }
    }

    @Test
    fun `insertOrIncrementViews increments counter for existing visitor`() {
        // given
        viewersRepository.save(ViewerModel(0, "Karzi", 100))

        // when
        viewersService.insertOrIncrementViews("Karzi")

        // then
        assertThat(viewersService.allViewers())
            .hasSize(1)
            .first()
            .satisfies {
                assertThat(it.username).isEqualTo("Karzi")
                assertThat(it.visits).isEqualTo(101)
            }
    }
}
