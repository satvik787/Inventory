package com.aai_project.inventory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.aai_project.inventory.database.Equipment

class EquipmentFragment:Fragment() {

    private val viewModel:EquipmentViewModel by lazy {
        ViewModelProvider(this).get(EquipmentViewModel::class.java)
    }

    private lateinit var serialText:TextView
    private lateinit var nameText:TextView
    private lateinit var dateBtn:Button
    private lateinit var downloadBtn:Button
    private lateinit var qrImage:ImageView
    private lateinit var equipment: Equipment

    companion object{
        const val KEY_EQUIPMENT_ID = "equipment_id"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.initialize(arguments?.getInt(KEY_EQUIPMENT_ID)!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_equipment,container,false)
    }

    override fun onViewCreated(v: View, savedInstanceState: Bundle?) {
        super.onViewCreated(v, savedInstanceState)
        serialText = v.findViewById(R.id.text_serial)
        nameText = v.findViewById(R.id.text_equipment_name)
        downloadBtn = v.findViewById(R.id.btn_download)
        dateBtn = v.findViewById(R.id.btn_date)
        qrImage = v.findViewById(R.id.qrImage)
        viewModel.getEquipment().observe(viewLifecycleOwner,{
            equipment = it
            updateUi(it)
        })
    }
    private fun updateUi(obj: Equipment){
        serialText.text = obj.serialNumber.toString()
        nameText.text = obj.equipmentName
        dateBtn.text = obj.dateOfInstallation.toString()
    }

}