package com.example.okta.testslices.repository

import com.example.okta.testslices.entity.Country
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace.*
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest

@DataJpaTest
@AutoConfigureTestDatabase(replace = ANY)
class CountryRepositoryTest {
    @Autowired
    private lateinit var countryRepository: CountryRepository

    @Test
    fun `insert two countries and find all`() {
        // given
        countryRepository.saveAll(
            listOf(
                Country(0, "Afghanistan", 38_928_000),
                Country(0, "Albania", 2_000_000)
            )
        )
        countryRepository.flush()

        // when
        val countries = countryRepository.findAll()

        // then
        assertThat(countries)
            .hasSize(2)
            .extracting<String>(Country::name)
            .containsOnly("Afghanistan", "Albania")
    }

    @Test
    fun `find country by first characters`() {
        // given
        countryRepository.saveAll(
            listOf(
                Country(0, "Canada", 37_000_000),
                Country(0, "United States of America", 228_000_000),
                Country(0, "United Kingdom", 2_000_000)
            )
        )
        countryRepository.flush()

        // when
        val countries = countryRepository.findCountriesByNameStartingWith("United")

        // then
        assertThat(countries)
            .hasSize(2)
            .extracting<String>(Country::name)
            .containsOnly("United States of America", "United Kingdom")
    }
}
