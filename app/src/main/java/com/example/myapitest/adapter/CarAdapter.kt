package com.example.myapitest.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.myapitest.R
import com.example.myapitest.model.Car
import com.squareup.picasso.Picasso

class CarAdapter(
    private val cars: List<Car>
): RecyclerView.Adapter<CarAdapter.CarViewHolder>() {
    class CarViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val imageView: ImageView = view.findViewById(R.id.image)
        val yearView: TextView = view.findViewById(R.id.year)
        val nameView: TextView = view.findViewById(R.id.model)
        val licenceView: TextView = view.findViewById(R.id.license)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_car_layout,parent,false)
        return CarViewHolder(view)
    }

    override fun getItemCount(): Int = cars.size

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        var car = cars[position]
        holder.yearView.text = car.value.year
        holder.nameView.text = car.value.name
        holder.licenceView.text = car.value.licence

        Picasso.get()
            .load(car.value.imageUrl)
            .into(holder.imageView)
    }
}
