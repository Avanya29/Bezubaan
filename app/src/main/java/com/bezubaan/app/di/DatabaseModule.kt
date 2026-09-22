package com.bezubaan.app.di

import android.content.Context
import androidx.room.Room
import com.bezubaan.app.core.common.Constants
import com.bezubaan.app.core.database.BezubaanDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideBezubaanDatabase(@ApplicationContext context: Context): BezubaanDatabase {
        return Room.databaseBuilder(
            context,
            BezubaanDatabase::class.java,
            Constants.DATABASE_NAME
        )
        .fallbackToDestructiveMigration() // Appropriate for early development
        .build()
    }
    
    // Provide DAOs here as they are created
}
