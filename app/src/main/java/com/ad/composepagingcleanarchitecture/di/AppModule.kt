package com.ad.composepagingcleanarchitecture.di

import android.content.Context
import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.room.Room
import com.ad.composepagingcleanarchitecture.data.local.BDatabase
import com.ad.composepagingcleanarchitecture.data.local.BEntity
import com.ad.composepagingcleanarchitecture.data.remote.BApi
import com.ad.composepagingcleanarchitecture.data.remote.BRRemoteMediator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton


@OptIn(ExperimentalPagingApi::class)
@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideBDatabase(@ApplicationContext context: Context): BDatabase {
        return Room.databaseBuilder(
            context,
            BDatabase::class.java,
            "b.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideBApi(): BApi {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        return Retrofit.Builder()
            .client(client)
            .baseUrl(BApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
            .create()
    }

    @Provides
    @Singleton
    fun BPagers(bDatabase: BDatabase, bApi: BApi): Pager<Int, BEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = BRRemoteMediator(
                bDb = bDatabase,
                bApi = bApi
            ),
            pagingSourceFactory = {
                bDatabase.dao.pagingSource()
            }
        )
    }
}