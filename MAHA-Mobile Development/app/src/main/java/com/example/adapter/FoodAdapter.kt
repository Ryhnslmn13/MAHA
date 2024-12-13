package com.example.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mahaapp.R

class FoodAdapter(private val foodList: List<FoodItem>) : RecyclerView.Adapter<FoodAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_health, parent, false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        val foodItem = foodList[position]
        holder.bind(foodItem)
    }

    override fun getItemCount(): Int = foodList.size

    inner class FoodViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private val title: TextView = view.findViewById(R.id.tvTitle)
        private val description: TextView = view.findViewById(R.id.tvDescription)
        private val image: ImageView = view.findViewById(R.id.ivFoodImage)

        fun bind(foodItem: FoodItem) {
            title.text = foodItem.name
            description.text = foodItem.description
            image.setImageResource(foodItem.imageResId)
        }
    }
}

data class FoodItem(val name: String, val description: String, val imageResId: Int)
