package com.ad.composepagingcleanarchitecture.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [BEntity::class],
    version = 1
)
abstract class BDatabase : RoomDatabase() {

    abstract val dao: BDao
}