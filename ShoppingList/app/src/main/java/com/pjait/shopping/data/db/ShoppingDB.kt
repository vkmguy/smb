package com.pjait.shopping.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pjait.shopping.data.db.entity.ShoppingItem

@Database(entities = [ShoppingItem::class], version=1)
abstract class ShoppingDB: RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao

    companion object {
        @Volatile
        private var instance: ShoppingDB? = null

        fun getDatabase(context: Context): ShoppingDB {
            if (instance != null) {
                return instance!!
            }
            instance = Room.databaseBuilder(
                context.applicationContext,
                ShoppingDB::class.java,
                "ShoppingDatabase"
            )
                .allowMainThreadQueries()
                .build()
            return instance!!
        }
    }
}