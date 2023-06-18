package com.ad.composepagingcleanarchitecture.data.remote

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.ad.composepagingcleanarchitecture.data.local.BDatabase
import com.ad.composepagingcleanarchitecture.data.local.BEntity
import retrofit2.HttpException
import java.io.IOException

@OptIn(ExperimentalPagingApi::class)
class BRRemoteMediator(
    private val bDb: BDatabase,
    private val bApi: BApi
) : RemoteMediator<Int, BEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, BEntity>
    ): MediatorResult {
        return try {
            val loadKey = when (loadType) {
                LoadType.REFRESH -> 1
                LoadType.PREPEND -> return MediatorResult.Success(
                    endOfPaginationReached = true
                )

                LoadType.APPEND -> {
                    val lastItem = state.lastItemOrNull()
                    if (lastItem == null) {
                        1
                    } else {
                        (lastItem.id / state.config.pageSize) + 1
                        1
                    }
                }
            }

            val b = bApi.getBs(
                page = loadKey,
                pageCount = state.config.pageSize
            )

            bDb.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    bDb.dao.clearAll()
                }
                val bEntity = b.map { it.toBEntity() }
                bDb.dao.upsertAll(bEntity)
            }
            MediatorResult.Success(
                endOfPaginationReached = b.isEmpty()
            )
        } catch (e: IOException) {
            MediatorResult.Error(e)
        } catch (e: HttpException) {
            MediatorResult.Error(e)
        }
    }
}