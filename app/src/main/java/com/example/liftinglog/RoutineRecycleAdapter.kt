package com.example.liftinglog

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView



class RoutineRecycleAdapter(private val context: Context, private val routines: List<Routine>): RecyclerView.Adapter<RoutineRecycleAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textRoutineName = itemView.findViewById<TextView>(R.id.routineNameText)
        val routinesCard = itemView.findViewById<CardView>(R.id.routinesCardView)

        var routinePosition = 0


        init {
            routinesCard.setOnClickListener{ view ->
                val intent = Intent(context, ActiveRoutineActivity::class.java)
                intent.putExtra(ROUTINE_POSITION_KEY, routinePosition)
                context.startActivity(intent)
            }


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.routines_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return routines.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val routine = routines[position]

        holder.routinePosition = position
        holder.textRoutineName.text = routine.name


    }
}