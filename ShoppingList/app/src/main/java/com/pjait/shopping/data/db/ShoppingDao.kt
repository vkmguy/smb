package com.pjait.shopping.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.pjait.shopping.data.db.entity.ShoppingItem

@Dao
interface ShoppingDao {

    @Query("SELECT * FROM ShoppingItem")
    fun getShoppingItems(): LiveData<List<ShoppingItem>>

    @Insert
    fun insert(item: ShoppingItem)

    @Update
    fun edit(item: ShoppingItem)

    @Delete
    fun delete(item: ShoppingItem)

    @Query("DELETE from ShoppingItem")
    fun deleteAll()
}