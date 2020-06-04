package com.example.liftinglog

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import java.util.concurrent.TimeUnit

const val HISTORY_ROUTINE_POSITION_KEY = "HISTORY_ROUTINE_POSITION"

class HistoryRoutineRecycleAdapter(val context: Context, val historyRoutines: MutableList<Routine>) : RecyclerView.Adapter<HistoryRoutineRecycleAdapter.ViewHolder>() {

    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val name = itemView.findViewById<TextView>(R.id.historyRoutineNameText)
        val timeSince = itemView.findViewById<TextView>(R.id.historyRoutineTimeSinceText)
        var routinePosition = 0

        val historyRoutineCard = itemView.findViewById<CardView>(R.id.historyRoutinesCardView)

        init {
            historyRoutineCard.setOnClickListener{
                val intent = Intent(context, HistoryRoutineSummaryActivity::class.java)
                intent.putExtra(HISTORY_ROUTINE_POSITION_KEY, routinePosition)
                context.startActivity(intent)
            }
        }


    }

    fun compareDates(date: Date) : String{
        val todayDate = Date()
        val diff: Long = todayDate.getTime() - date.getTime()
        var timeSince = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS).toString() + " days ago"
        if (timeSince == "0 days ago") timeSince = "Today"
        return timeSince

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.history_routine_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return DataManager.historyRoutines.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = DataManager.historyRoutines[position].name
        holder.timeSince.text = compareDates(DataManager.historyRoutines[position].finishedDate!!)
        holder.routinePosition = position

    }
}