package com.overcom.bananaapp.di

import android.content.Context
import androidx.room.Room
import com.overcom.bananaapp.data.database.BananaDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
object RoomModule {

    private const val PRODUCTS_DATABASE_NAME = "products_database"

    @Provides
    fun provideRoom(@ApplicationContext context: Context) =
        Room.databaseBuilder(context, BananaDatabase::class.java, PRODUCTS_DATABASE_NAME).build()

    @Provides
    fun provideProductsDao(db: BananaDatabase) = db.getProductsDao()
}