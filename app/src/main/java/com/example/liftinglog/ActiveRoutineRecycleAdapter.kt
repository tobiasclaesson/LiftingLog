package com.example.liftinglog

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ActiveRoutineRecycleAdapter(private val context: Context, private val routine: Routine) : RecyclerView.Adapter<ActiveRoutineRecycleAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val exerciseHeader = itemView.findViewById<TextView>(R.id.activeRoutineNameText)
        val setsRecyclerView = itemView.findViewById<RecyclerView>(R.id.activeRoutineSetsRecyclerView)
        val addSetButton = itemView.findViewById<Button>(R.id.activeRoutineAddSetButton)
        val removeRoutineButton = itemView.findViewById<ImageButton>(R.id.removeRoutineButton)
        var exercisePosition = 0


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.active_routine_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {

        return routine.exercises!!.size

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val exercise = routine.exercises!![position]
        holder.exercisePosition = position

        holder.exerciseHeader.text = exercise.name.toString()


        holder.setsRecyclerView.layoutManager = LinearLayoutManager(holder.setsRecyclerView.context)
        holder.setsRecyclerView.adapter = SetsRecycleAdapter(holder.setsRecyclerView.context, holder.setsRecyclerView, routine.exercises!![holder.exercisePosition])



        holder.addSetButton.setOnClickListener{ view ->
            routine.exercises!![holder.exercisePosition].addSet()
            holder.setsRecyclerView.adapter?.notifyDataSetChanged()
        }
    }
}