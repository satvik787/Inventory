package com.aai_project.inventory.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
class Equipment(
    @PrimaryKey(autoGenerate = true) val equipmentId: Int = 0,
    var serialNumber: Int,
    var equipmentName: String,
    var dateOfInstallation: Date = Date(),
)
