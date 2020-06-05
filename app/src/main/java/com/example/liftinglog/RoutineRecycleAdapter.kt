package com.example.liftinglog

import android.content.Context
import android.content.Intent
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.view.isInvisible
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore


class RoutineRecycleAdapter(private val context: Context, private val routines: List<Routine>, val noRoutinesText: TextView, val auth: FirebaseAuth, val db: FirebaseFirestore): RecyclerView.Adapter<RoutineRecycleAdapter.ViewHolder>() {


    private val layoutInflater = LayoutInflater.from(context)

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val textRoutineName = itemView.findViewById<TextView>(R.id.routineNameText)
        val routinesCard = itemView.findViewById<CardView>(R.id.routinesCardView)
        val removeRoutineButton = itemView.findViewById<ImageButton>(R.id.removeRoutineButton)

        var routinePosition = 0


        init {
            routinesCard.setOnClickListener{ view ->
                val intent = Intent(context, ActiveRoutineActivity::class.java)
                intent.putExtra(ROUTINE_POSITION_KEY, routinePosition)
                context.startActivity(intent)
            }



        }

    }

    fun deleteRoutine(routine: Routine){
        val user = auth.currentUser ?: return

        db.collection("users").document(user.uid).collection("routines").document(routine.docId!!)
            .delete()
            .addOnSuccessListener { Log.d("!!!", "DocumentSnapshot successfully deleted!") }
            .addOnFailureListener { e -> Log.w("!!!", "Error deleting document", e) }
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

        noRoutinesText.visibility = View.INVISIBLE

        holder.removeRoutineButton.setOnClickListener{
            deleteRoutine(DataManager.routines[position])
        }
    }
}