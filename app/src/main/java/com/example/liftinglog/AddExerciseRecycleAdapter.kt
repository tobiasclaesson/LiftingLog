package com.example.liftinglog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot


class AddExerciseRecycleAdapter(val context: Context, val exercises: MutableList<Exercise>) : RecyclerView.Adapter<AddExerciseRecycleAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)



    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val exerciseNameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val exerciseRecyclerView = itemView.findViewById<RecyclerView>(R.id.exercisesRecyclerView)

        init {


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
    }




}