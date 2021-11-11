package com.pjait.shopping.ui.shoppingList

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.pjait.shopping.data.db.ShoppingDB
import com.pjait.shopping.data.db.entity.ShoppingItem
import com.pjait.shopping.data.db.repository.ShoppingRepository

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ShoppingViewModel(application: Application):
    AndroidViewModel(application) {

    private val repo: ShoppingRepository = ShoppingRepository(ShoppingDB.getDatabase(application).shoppingDao())
    val allItem: LiveData<List<ShoppingItem>> = repo.allItems

    fun allItems() = repo.allItems

    fun insert(item: ShoppingItem) = CoroutineScope(Dispatchers.Main).launch {
        repo.insert(item)
    }

    fun delete(item: ShoppingItem) = CoroutineScope(Dispatchers.Main).launch {
        repo.delete(item)
    }

    fun edit(item: ShoppingItem) = CoroutineScope(Dispatchers.Main).launch {
        repo.update(item)
    }

    fun deleteAll() = CoroutineScope(Dispatchers.Main).launch {
        repo.deleteAll()
    }
}