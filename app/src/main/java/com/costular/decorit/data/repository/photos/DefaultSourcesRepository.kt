package com.costular.decorit.data.repository.photos

import com.costular.decorit.R
import com.costular.decorit.data.SourceConstants.UNSPLASH
import com.costular.decorit.domain.model.Source
import com.costular.decorit.domain.repository.SourcesRepository

class DefaultSourcesRepository : SourcesRepository {

    companion object {
        val Unsplash = Source(
            UNSPLASH,
            "Unsplash",
            "https://unsplash.com/",
            R.drawable.ic_unsplash
        )
    }

    private val sources: List<Source> = listOf(
        Unsplash
        // TODO add sources
    )

    override suspend fun getSources(): List<Source> = sources

    override suspend fun getSourceById(id: String): Source? = sources.find { it.id == id }

}