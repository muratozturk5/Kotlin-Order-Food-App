package com.muratozturk.kotlinmvvmproject.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.muratozturk.kotlinmvvmproject.data.models.ProductsBasketRoomModel

@Database(entities = [ProductsBasketRoomModel::class], version = 1)
abstract class ProductsBasketRoomDatabase : RoomDatabase() {
    abstract fun productsBasketDAOInterface(): ProductsBasketDAOInterface

    companion object {
        private var instance: ProductsBasketRoomDatabase? = null

        fun productsBasketRoomDatabase(context: Context): ProductsBasketRoomDatabase? {

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    ProductsBasketRoomDatabase::class.java,
                    "productsbasketdatabase.db"
                )
                    .allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}