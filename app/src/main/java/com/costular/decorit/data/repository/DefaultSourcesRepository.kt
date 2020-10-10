package com.costular.decorit.data.repository

import com.costular.decorit.R
import com.costular.decorit.data.SourceConstants.UNSPLASH
import com.costular.decorit.domain.model.Source
import com.costular.decorit.domain.repository.SourcesRepository
import com.costular.decorit.util.SourceId

class DefaultSourcesRepository : SourcesRepository {

    private val sources: List<Source> = listOf(
        Source(
            SourceId(UNSPLASH),
            "Unsplash",
            "https://unsplash.com/",
            R.drawable.ic_unsplash
        )
        // TODO add sources
    )

    override suspend fun getSources(): List<Source> = sources

    override suspend fun getSourceById(id: SourceId): Source? = sources.find { it.id == id }

}