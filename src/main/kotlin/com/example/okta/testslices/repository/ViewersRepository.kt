package com.example.okta.testslices.repository

import com.example.okta.testslices.entity.ViewerModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import javax.persistence.EntityManager
import javax.transaction.Transactional

@Repository
interface ViewersRepository : JpaRepository<ViewerModel, Long> {
    fun findByUsername(username: String): ViewerModel?

    @Modifying(clearAutomatically = true)
    @Query("UPDATE ViewerModel SET visits = visits + 1 WHERE username = ?1")
    fun incrementVisit(username: String)

    @Query("SELECT AVG(visits) FROM ViewerModel")
    fun averageViewsPerUser(): Double
}
