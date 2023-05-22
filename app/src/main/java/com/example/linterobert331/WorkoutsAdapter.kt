package com.example.linterobert331

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import pl.droidsonroids.gif.GifImageView

class WorkoutsAdapter(private var newList: ArrayList<Workouts>):
    RecyclerView.Adapter<WorkoutsAdapter.WorkoutsViewHolder>() {
    class WorkoutsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val titleImage = itemView.findViewById<GifImageView>(R.id.title_image)
        val tvHeading = itemView.findViewById<TextView>(R.id.tvHeading)
    }

    public fun setFilteredList(filteredList: ArrayList<Workouts>){
        this.newList = filteredList
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkoutsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.workouts_list,
            parent, false)
        return WorkoutsViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return newList.size;
    }

    override fun onBindViewHolder(holder: WorkoutsViewHolder, position: Int) {
        val currentItem = newList[position]
        holder.titleImage.setImageResource(currentItem.titleImage)
        holder.tvHeading.text = currentItem.heading
    }
}