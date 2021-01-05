package com.aai_project.inventory

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aai_project.inventory.database.Equipment
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class EquipmentListFragment:Fragment() {

    private val viewModel:EquipmentListViewModel by lazy {
        ViewModelProvider(this).get(EquipmentListViewModel::class.java)
    }
    private lateinit var qrScanner:IntentIntegrator
    private var navigation: Navigation? = null
    companion object{
        const val EquipmentId = "equipment_id"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        navigation = context as Navigation
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        qrScanner = IntentIntegrator.forSupportFragment(this)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_equipment_list,container,false)
    }

    override fun onDetach() {
        super.onDetach()
        navigation = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        menu.clear()
        inflater.inflate(R.menu.fragment_equipment_list,menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.scan_equipment){
            qrScanner.initiateScan()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode,resultCode,data)
        if (resultCode == Activity.RESULT_OK && result != null) {
            if (result.contents == null) {
                Toast.makeText(
                    requireContext(),
                    getString(R.string.result_not_found),
                    Toast.LENGTH_LONG
                ).show()
            } else {
                try {
                    val obj = JSONObject(result.contents)
                    val a = obj.getInt(EquipmentId)
                    navigation?.onNavigate(a)
                } catch (e: JSONException) {
                    e.printStackTrace()

                    Toast.makeText(requireContext(), result.contents, Toast.LENGTH_LONG).show()
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        val adapter = EquipmentAdapter(listOf())
        recyclerView.adapter = adapter
        viewModel.equipmentList.observe(viewLifecycleOwner,{
            adapter.list = it
            adapter.notifyDataSetChanged()
        })
    }



    private inner class EquipmentAdapter(var list:List<Equipment>):RecyclerView.Adapter<EquipmentItemHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EquipmentItemHolder {
            val view = requireActivity().layoutInflater.inflate(R.layout.item_equipment, parent,false)
            return EquipmentItemHolder(view)
        }

        override fun onBindViewHolder(holder: EquipmentItemHolder, position: Int) {
            holder.build(list[position])
        }

        override fun getItemCount() = list.size

    }

    private inner class EquipmentItemHolder(view: View): RecyclerView.ViewHolder(view) {
        private val nameText = itemView.findViewById<TextView>(R.id.name)
        private val serialText = itemView.findViewById<TextView>(R.id.serial_number)
        private val dateText = itemView.findViewById<TextView>(R.id.installed_date)
        private var id:Int = 0
        init {
            itemView.setOnClickListener {
                navigation?.onNavigate(id)
            }
        }
        fun build(obj: Equipment){
            id = obj.equipmentId
            nameText.text = obj.equipmentName
            serialText.text = "Serial# " + obj.serialNumber
            val c = Calendar.getInstance()
            c.time = obj.dateOfInstallation
            val d = c.get(Calendar.DAY_OF_MONTH).toString()+"/"+c.get(Calendar.MONTH)+"/"+c.get(Calendar.YEAR)
            dateText.text = d
        }

    }

    interface Navigation{
        fun onNavigate(id: Int)
    }


}