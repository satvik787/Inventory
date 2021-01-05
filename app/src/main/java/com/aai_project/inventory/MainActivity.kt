package com.aai_project.inventory

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate

class MainActivity : AppCompatActivity(),EquipmentListFragment.Navigation {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)

        val f = EquipmentListFragment()
        f.setHasOptionsMenu(true)
        supportFragmentManager.beginTransaction()
            .add(R.id.container,f)
            .commit()
    }

    override fun onNavigate(id: Int) {
        val f = EquipmentFragment()
        f.arguments = Bundle().apply {
            putInt(EquipmentFragment.KEY_EQUIPMENT_ID,id)
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.container,f)
                .addToBackStack(null)
                .commit()
    }
}