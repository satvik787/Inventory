package com.aai_project.inventory

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.aai_project.inventory.database.Equipment

class EquipmentViewModel:ViewModel() {
    private lateinit var equipment:LiveData<Equipment>

    fun initialize(id: Int){
        equipment = InventoryRepository.get().dao.getEquipment(id)
    }
    fun getEquipment() = equipment
}