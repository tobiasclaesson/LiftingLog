package com.example.liftinglog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddRoutineActivity : AppCompatActivity() {

    var exerciseList = mutableListOf<Exercise>(Exercise("Benchpress", "hej", 1, 0, 0.0))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_routine)

        val addExerciseButton = findViewById<Button>(R.id.addExerciseButton)
        val routineNameText = findViewById<EditText>(R.id.routineNameText)
        val addRoutineRecyclerView = findViewById<RecyclerView>(R.id.addRoutineRecyclerView)
        val adapter = AddRoutineRecycleAdapter(this, exerciseList)

        addRoutineRecyclerView.layoutManager = LinearLayoutManager(this)

        addRoutineRecyclerView.adapter = adapter

        routineNameText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                routineNameText.setText("")
            }
        }
        addExerciseButton.setOnClickListener{ view ->
            exerciseList.add(Exercise("Dubbelhakspress", "hej", 1, 0, 0.0))
            addRoutineRecyclerView.adapter?.notifyDataSetChanged()
        }



    }

}
