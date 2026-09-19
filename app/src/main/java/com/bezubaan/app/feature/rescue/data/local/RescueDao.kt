package com.bezubaan.app.feature.rescue.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface RescueDao {
    @Query("SELECT * FROM rescue_cases WHERE isPendingSync = 0")
    fun getCachedRescues(): Flow<List<RescueCaseEntity>>

    @Query("SELECT * FROM rescue_cases WHERE isPendingSync = 1")
    fun getPendingSyncRescues(): Flow<List<RescueCaseEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRescues(rescues: List<RescueCaseEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRescue(rescue: RescueCaseEntity)

    @Query("DELETE FROM rescue_cases WHERE id = :id")
    suspend fun deleteRescue(id: String)
}
