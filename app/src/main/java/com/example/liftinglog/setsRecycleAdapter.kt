package com.example.liftinglog

import android.content.Context
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.RecyclerView

class SetsRecycleAdapter(private val context: Context,
                         val exercise: Exercise) : RecyclerView.Adapter<SetsRecycleAdapter.ViewHolder>() {


    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val setText = itemView.findViewById<TextView>(R.id.setNumberText)
        val repText = itemView.findViewById<EditText>(R.id.repNumberText)
        val weightText = itemView.findViewById<EditText>(R.id.weightNumberText)

        init {
            repText.setOnFocusChangeListener { v, hasFocus ->
                if (hasFocus){
                    Log.d("!!!", "focuuus")
                }
                else{
                    val data = repText.text

                    exercise.reps = data.toString().toInt()
                }

            }
        }

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
        holder.repText.setText(exercise.reps.toString())
        holder.weightText.setText(exercise.weight.toString())
    }
}