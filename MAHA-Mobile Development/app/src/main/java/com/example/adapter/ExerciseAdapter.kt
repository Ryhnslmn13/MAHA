package com.example.mahaapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.mahaapp.databinding.ItemExerciseBinding

class ExerciseAdapter(private val exerciseList: List<ExerciseItem>) :
    RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExerciseViewHolder {
        val binding = ItemExerciseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ExerciseViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ExerciseViewHolder, position: Int) {
        val exercise = exerciseList[position]
        holder.bind(exercise)
    }

    override fun getItemCount(): Int = exerciseList.size

    inner class ExerciseViewHolder(private val binding: ItemExerciseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(exercise: ExerciseItem) {
            binding.tvTitle.text = exercise.name
            binding.tvDescription.text = exercise.description
            binding.ivExerciseImage.setImageResource(exercise.imageResource)
        }
    }
}

data class ExerciseItem(
    val name: String,
    val description: String,
    val imageResource: Int
)
