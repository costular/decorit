package com.costular.decorit.domain.repository

import com.costular.decorit.domain.model.Source

interface SourcesRepository {

    suspend fun getSources(): List<Source>
    suspend fun getSourceById(id: String): Source?

}