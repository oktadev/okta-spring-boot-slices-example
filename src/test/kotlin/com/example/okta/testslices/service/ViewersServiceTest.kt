package com.example.okta.testslices.service

import ch.qos.logback.classic.ViewStatusMessagesServlet
import com.example.okta.testslices.entity.ViewerModel
import com.example.okta.testslices.repository.ViewersRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.FilterType

@DataJpaTest(includeFilters = [
    ComponentScan.Filter(ViewersService::class, type = FilterType.ASSIGNABLE_TYPE)
])
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
internal class ViewersServiceTest {
    @Autowired
    private lateinit var viewersService: ViewersService

    @Autowired
    private lateinit var viewersRepository: ViewersRepository

    @Test
    fun `insertOrIncrement creates a new visitor record when need`() {
        // when
        viewersService.insertOrIncrement("Jafari")

        // then
        Assertions.assertThat(viewersService.totalUniqueVisitors())
                .isEqualTo(1)
        Assertions.assertThat(viewersService.getByUsername("Jafari"))
                .extracting<Int> { it?.visits }
                .isEqualTo(1)
    }

    @Test
    fun `insertOrIncrement increments counter for existing visitor`() {
        // given
        viewersRepository.save(ViewerModel(0, "Karzi", 100))

        // when
        viewersService.insertOrIncrement("Karzi")

        // then
        Assertions.assertThat(viewersService.totalUniqueVisitors())
                .isEqualTo(1)
        Assertions.assertThat(viewersService.getByUsername("Karzi"))
                .extracting<Int> { it?.visits }
                .isEqualTo(101)
    }

}