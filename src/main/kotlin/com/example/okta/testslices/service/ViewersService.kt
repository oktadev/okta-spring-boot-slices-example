package com.example.okta.testslices.service

import com.example.okta.testslices.entity.ViewerModel
import com.example.okta.testslices.repository.ViewersRepository
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class ViewersService(
    private val repo: ViewersRepository
) {
    @Transactional
    fun insertOrIncrementViews(username: String): ViewerModel {
        // find an existing record for the viewer or create a new one
        val viewer = repo.findByUsername(username)
            ?: repo.save(ViewerModel(0, username, 0))

        // record current visit
        repo.incrementVisit(username)

        // retrieve the most recent version
        return repo.getById(viewer.id)
    }

    fun averagesViewsPerUser(): Double =
        repo.averageViewsPerUser()

    fun allViewers(): List<ViewerModel> =
        repo.findAll(Sort.by(ViewerModel::username.name))
}
