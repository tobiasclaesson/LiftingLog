package com.example.liftinglog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddRoutineRecycleAdapter(private val context: Context, private val exercises: List<Exercise>) : RecyclerView.Adapter<AddRoutineRecycleAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val exerciseHeader = itemView.findViewById<TextView>(R.id.addRoutineNameText)
        val setsRecyclerView = itemView.findViewById<RecyclerView>(R.id.setsRecyclerView)

        val addSetButton = itemView.findViewById<Button>(R.id.addSetButton)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.add_routine_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = exercises[position]



        holder.setsRecyclerView.layoutManager = LinearLayoutManager(holder.setsRecyclerView.context)
        holder.setsRecyclerView.adapter = SetsRecycleAdapter(holder.setsRecyclerView.context, exercise)

        holder.addSetButton.setOnClickListener{ view ->
            exercise.addSet()
            holder.setsRecyclerView.adapter?.notifyDataSetChanged()
        }



        holder.exerciseHeader.text = exercise.name.toString()


    }
}