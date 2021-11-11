package com.pjait.shopping.data.db.repository

import com.pjait.shopping.data.db.ShoppingDao
import com.pjait.shopping.data.db.entity.ShoppingItem

class ShoppingRepository(private var shoppingDao: ShoppingDao) {
    val allItems = shoppingDao.getShoppingItems()

    fun insert(item: ShoppingItem) = shoppingDao.insert(item)

    fun delete(item: ShoppingItem) = shoppingDao.delete(item)

    fun update(item: ShoppingItem) = shoppingDao.edit(item)

    fun deleteAll() = shoppingDao.deleteAll()
}