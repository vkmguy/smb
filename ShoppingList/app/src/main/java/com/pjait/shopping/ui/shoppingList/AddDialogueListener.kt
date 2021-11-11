package com.pjait.shopping.ui.shoppingList

import com.pjait.shopping.data.db.entity.ShoppingItem

interface AddDialogueListener {

    fun onAddButtonClicked(item: ShoppingItem)
}