package com.example.liftinglog

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView


class AddExerciseRecycleAdapter(val context: Context, val exercises: MutableList<Exercise>, val addExerciseActivity: AddExerciseActivity) : RecyclerView.Adapter<AddExerciseRecycleAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val exerciseNameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val exerciseRecyclerView = itemView.findViewById<RecyclerView>(R.id.exercisesRecyclerView)
        val exerciseView = itemView.findViewById<ConstraintLayout>(R.id.exerciseView)

        var exercisePosition = 0

        init {

            exerciseView.setOnClickListener{view ->
                val exercise = exercises[exercisePosition]

                DataManager.newExercise = exercise
                (context as Activity).finish()
                println("!!! ${DataManager.newExercise.name}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.exercise_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.exerciseNameTextView.text = exercises[position].name

        holder.exercisePosition = position
    }




}