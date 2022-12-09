package com.overcom.bananaapp.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.overcom.bananaapp.data.database.dao.ProductsDao
import com.overcom.bananaapp.data.database.entities.ProductsEntity

@Database(entities = [ProductsEntity::class], version = 1)
abstract class BananaDatabase : RoomDatabase() {

    abstract fun getProductsDao(): ProductsDao
}