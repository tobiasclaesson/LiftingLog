package com.example.liftinglog

import android.content.Context
import android.util.Log
import android.view.*
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.RecyclerView

class SetsRecycleAdapter(private val context: Context,
                         val setsRecycleView: RecyclerView,
                         val exercise: Exercise) : RecyclerView.Adapter<SetsRecycleAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val setText = itemView.findViewById<TextView>(R.id.setNumberText)
        val repText = itemView.findViewById<EditText>(R.id.repNumberText)
        val weightText = itemView.findViewById<EditText>(R.id.weightNumberText)
        val removeButton = itemView.findViewById<ImageButton>(R.id.removeButton)





        var repPosition = 0

        init {
            removeButton.setOnClickListener{
                removeSet(repPosition)
                setsRecycleView.adapter!!.notifyDataSetChanged()
            }

        }

    }

    fun removeSet(repPosition: Int){
        exercise.removeSet(repPosition)
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
        holder.repPosition = position



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