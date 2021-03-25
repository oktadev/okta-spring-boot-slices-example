package com.example.okta.testslices.repository

import com.example.okta.testslices.entity.Country
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CountryRepository : JpaRepository<Country, Long> {
    fun findCountriesByNameStartingWith(namePrefix: String): List<Country>
}
