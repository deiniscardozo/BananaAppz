package com.overcom.bananaapp.data.database.dao

import androidx.room.*
import com.overcom.bananaapp.data.database.entities.ProductsEntity
import com.overcom.bananaapp.data.model.ProductsModel
import com.overcom.bananaapp.domain.model.Products

@Dao
interface ProductsDao {

    @Query("SELECT * FROM products_table ORDER BY reference ASC")
    suspend fun getAll(): List<ProductsEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(products: List<ProductsEntity>)

    @Delete
    suspend fun deleteAll(products: List<ProductsEntity>)
}