package com.pjait.shopping.data.db.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
class ShoppingItem(
    @PrimaryKey(autoGenerate = true) var id: Int =0,
    var name: String,
    var price: Int,
    var amount: Int,
    var isBought: Boolean)
{
}