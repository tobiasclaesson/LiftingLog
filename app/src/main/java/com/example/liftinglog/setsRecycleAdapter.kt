package com.example.liftinglog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SetsRecycleAdapter(private val context: Context,
                         val exercise: Exercise) : RecyclerView.Adapter<SetsRecycleAdapter.ViewHolder>() {


    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val setText = itemView.findViewById<TextView>(R.id.setNumberText)
        val repText = itemView.findViewById<TextView>(R.id.repNumberText)
        val weightText = itemView.findViewById<TextView>(R.id.weightNumberText)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.set_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return exercise.sets
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setText.text = (position + 1).toString()
        holder.repText.text = exercise.reps.toString()
        holder.weightText.text = exercise.weight.toString()
    }
}