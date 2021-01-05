package com.aai_project.inventory.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface InventoryDao {

    @Query("SELECT * FROM EQUIPMENT WHERE equipmentId = :id")
    fun getEquipment(id: Int):LiveData<Equipment>

    @Query("SELECT * FROM ServiceRecord WHERE ownerId = :id")
    fun getServiceRecords(id: Int):LiveData<List<ServiceRecord>>

    @Query("SELECT * FROM EQUIPMENT")
    fun getEquipments():LiveData<List<Equipment>>

    @Insert
    fun insertEquipment(obj: Equipment)

    @Update
    fun updateEquipment(obj: Equipment)
}