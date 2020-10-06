package com.costular.decorit.domain.repository

import com.costular.decorit.domain.model.Source
import com.costular.decorit.util.SourceId

interface SourcesRepository {

    suspend fun getSources(): List<Source>
    suspend fun getSourceById(id: SourceId): Source?

}