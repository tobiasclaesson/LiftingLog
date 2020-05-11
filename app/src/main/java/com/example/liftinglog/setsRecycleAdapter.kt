package com.example.liftinglog

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class SetsRecycleAdapter(private val context: Context,
                         val exercise: Exercise) : RecyclerView.Adapter<SetsRecycleAdapter.ViewHolder>() {


    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val setsRecycleView = itemView.findViewById<RecyclerView>(R.id.setsRecyclerView)
        val setText = itemView.findViewById<TextView>(R.id.setNumberText)
        val repText = itemView.findViewById<EditText>(R.id.repNumberText)
        val weightText = itemView.findViewById<EditText>(R.id.weightNumberText)

        init {
            //setsRecycleView.adapter = SetsRecycleAdapter(setsRecycleView.context, exercise)


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.set_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return exercise.set
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setText.text = (position + 1).toString()
        holder.repText.setText(exercise.reps!![position].toString())
        holder.weightText.setText(exercise.weight!![position].toString())


        holder.repText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                Log.d("!!!", "focuuus")


            }
            else{
                val data = holder.repText.text

                exercise.updateReps(position, data.toString().toInt())
                exercise.reps!!.forEach {
                    Log.d("!!!", "Rep: $it")
                }
                //setsRecycleView.adapter?.notifyDataSetChanged()
                Log.d("!!!", "Focus gone")
            }

        }

        holder.weightText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                Log.d("!!!", "focuuus")
            }
            else{
                val data = holder.weightText.text

                exercise.updateWeight(position, data.toString().toDouble())

                exercise.weight!!.forEach {
                    Log.d("!!!", "Weight: $it")
                }
                //setsRecycleView.adapter?.notifyDataSetChanged()

                Log.d("!!!", "Focus gone")
            }

        }
    }
}