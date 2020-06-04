package com.example.liftinglog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import kotlinx.android.synthetic.main.activity_add_exercise.*

class AddExerciseActivity : AppCompatActivity() {

    val exercises = mutableListOf<Exercise>()
    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_exercise)
        this.title = "Add Exercise"

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        loadExercisesFromDB()

        val exercisesRecycleView = findViewById<RecyclerView>(R.id.exercisesRecyclerView)
        exercisesRecycleView.layoutManager = LinearLayoutManager(this)
        exercisesRecycleView.adapter = AddExerciseRecycleAdapter(this, exercises, AddExerciseActivity())




    }


    fun loadExercisesFromDB(){
        val user = auth.currentUser ?: return
        db.collection("exercises")
            .get()
            .addOnCompleteListener(OnCompleteListener<QuerySnapshot> { task ->
                if (task.isSuccessful) {
                    for (document in task.result!!) {
                        Log.d("!!!", document.id + " => " + document.data)
                        exercises.add(document.toObject(Exercise::class.java))
                        exercisesRecyclerView.adapter!!.notifyDataSetChanged()
                    }
                } else {
                    Log.w("!!!", "Error getting documents.", task.exception)
                }
            })
    }

}
