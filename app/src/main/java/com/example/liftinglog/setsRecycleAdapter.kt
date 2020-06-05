package com.example.liftinglog

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
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


        holder.repText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val data = holder.repText.text

                exercise.updateReps(position, data.toString().toInt())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

        holder.weightText.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val data = holder.weightText.text

                exercise.updateWeight(position, data.toString().toDouble())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

        })

    }
}