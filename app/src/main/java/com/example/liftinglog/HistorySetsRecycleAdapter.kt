package com.example.liftinglog

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class HistorySetsRecycleAdapter(private val context: Context,
                         val exercise: Exercise) : RecyclerView.Adapter<HistorySetsRecycleAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val setText = itemView.findViewById<TextView>(R.id.historySetNumberText)
        val repText = itemView.findViewById<TextView>(R.id.historyRepNumberText)
        val weightText = itemView.findViewById<TextView>(R.id.historyWeightNumberText)

    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.history_routine_summary_set_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return exercise.set
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setText.text = (position + 1).toString()
        holder.repText.text = exercise.reps!![position].toString()
        holder.weightText.text = exercise.weight!![position].toString()

    }
}