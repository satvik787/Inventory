package com.aai_project.inventory

import android.content.Context
import androidx.room.Room
import com.aai_project.inventory.database.Equipment
import com.aai_project.inventory.database.InventoryDatabase
import java.util.concurrent.Executors

class InventoryRepository private constructor(context: Context) {
    private val database:InventoryDatabase = Room.databaseBuilder(
        context,
        InventoryDatabase::class.java,
        name
    ).build()

    val dao = database.getDao()
    private val thread = Executors.newSingleThreadExecutor()

    companion object{
        private const val name = "inventory_DB"
        private var repository:InventoryRepository? = null
        fun initialize(context: Context){
            if (repository == null){
                repository = InventoryRepository(context)
            }
        }
        fun get() = repository!!
    }

    fun updateEquipment(obj: Equipment){
        thread.execute {
            dao.updateEquipment(obj)
        }
    }

}