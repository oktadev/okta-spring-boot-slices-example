package com.example.okta.testslices.service

import com.example.okta.testslices.entity.ViewerModel
import com.example.okta.testslices.repository.ViewersRepository
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ViewersService(
    private val repo: ViewersRepository
) {
    @Transactional
    fun insertOrIncrement(username: String): ViewerModel {
        // find an existing record for the viewer or create a new one
        val viewer = repo.findByUsername(username)
                ?: repo.save(ViewerModel(0, username, 0))

        // record current visit
        repo.incrementVisit(username)

        // retrieve the most recent version
        return repo.getOne(viewer.id)
    }

    fun getByUsername(username: String): ViewerModel?
        = repo.findByUsername(username)

    fun averagesViewsPerUser(): Double
        = repo.averageViewsPerUser()

    fun totalUniqueVisitors(): Long
        = repo.count()
}