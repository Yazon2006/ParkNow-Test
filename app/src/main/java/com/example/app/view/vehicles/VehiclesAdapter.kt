package com.example.app.view.vehicles

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.app.data.entity.Vehicle
import com.example.app.myapplication.R
import kotlinx.android.synthetic.main.item_vehicle.view.*
import javax.inject.Inject

class VehiclesAdapter @Inject constructor() : RecyclerView.Adapter<VehicleViewHolder>() {

	var onVehicleClick: (Vehicle) -> Unit = {}

	var vehicles: List<Vehicle> = listOf()
		set(value) {
			field = value
			notifyDataSetChanged()
		}

	override fun getItemId(position: Int): Long {
		return if (vehicles.isNotEmpty()) vehicles[position].id else 0L
	}

	override fun onCreateViewHolder(parent: ViewGroup, position: Int): VehicleViewHolder {
		return VehicleViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_vehicle, parent, false))
	}

	override fun getItemCount(): Int = vehicles.size

	override fun onBindViewHolder(holder: VehicleViewHolder, position: Int) {
		val vehicle = vehicles[position]
		holder.itemView.titleTextView.text = vehicle.vrn
		holder.itemView.setOnClickListener {
			onVehicleClick(vehicles[position])
		}
	}

}

class VehicleViewHolder(view: View) : RecyclerView.ViewHolder(view)