package com.bezubaan.app.core.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

import com.bezubaan.app.feature.auth.data.local.UserDao
import com.bezubaan.app.feature.auth.data.local.UserEntity
import com.bezubaan.app.feature.rescue.data.local.RescueDao
import com.bezubaan.app.feature.rescue.data.local.RescueCaseEntity

@Database(
    entities = [UserEntity::class, RescueCaseEntity::class],
    version = 1,
    exportSchema = true
)
@TypeConverters(Converters::class)
abstract class BezubaanDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun rescueDao(): RescueDao
}
