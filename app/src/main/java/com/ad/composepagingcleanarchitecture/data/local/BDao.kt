package com.ad.composepagingcleanarchitecture.data.local

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface BDao {

    @Upsert
    suspend fun upsertAll(bs: List<BEntity>)

    @Query("SELECT * FROM BEntity")
    fun pagingSource(): PagingSource<Int, BEntity>

    @Query("DELETE FROM BEntity")
    suspend fun clearAll()
}