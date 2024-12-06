package com.example.randomdog.ui


import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.randomdog.databinding.LayoutItemRecentBinding
import com.example.randomdog.db.Dog

class DogAdapter : RecyclerView.Adapter<DogAdapter.DogViewHolder>() {

    private val dogs = mutableListOf<Dog>()

    class DogViewHolder(private val binding: LayoutItemRecentBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dog: Dog, context: Context) {
            Glide.with(context).load(dog.url).into(binding.ivDog)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogViewHolder {
        val binding = LayoutItemRecentBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DogViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DogViewHolder, position: Int) {
        holder.bind(dogs[position], holder.itemView.context)
    }

    override fun getItemCount(): Int = dogs.size

    fun setDogs(newDogs: List<Dog>) {
        dogs.clear()
        dogs.addAll(newDogs)
        notifyDataSetChanged()
    }
}
