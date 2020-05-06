package com.example.liftinglog

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class AddRoutineActivity : AppCompatActivity() {

    var exerciseList = mutableListOf<Exercise>(Exercise("Bench press", "notinging", 1, 0, mutableListOf(0), mutableListOf(0.0)))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_routine)


        val addExerciseButton = findViewById<Button>(R.id.addExerciseButton)
        val saveRoutineButton = findViewById<Button>(R.id.saveRoutineButton)
        val routineNameText = findViewById<EditText>(R.id.routineNameText)
        val addRoutineRecyclerView = findViewById<RecyclerView>(R.id.addRoutineRecyclerView)
        val addRoutineAdapter = AddRoutineRecycleAdapter(this, exerciseList)
        val setsRecyclerView = findViewById<RecyclerView>(R.id.setsRecyclerView)


        addRoutineRecyclerView.layoutManager = LinearLayoutManager(this)

        addRoutineRecyclerView.adapter = addRoutineAdapter



        routineNameText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus && routineNameText.text == null){
                routineNameText.setText("")
            }
        }
        addExerciseButton.setOnClickListener{ view ->
            exerciseList.add(Exercise("Dubbelhakspress", "hej", 1, 0, mutableListOf(0), mutableListOf(0.0)))
            addRoutineRecyclerView.adapter?.notifyDataSetChanged()
        }
        saveRoutineButton.setOnClickListener{ view ->
            DataManager.routines.add(Routine(routineNameText.text.toString(), exerciseList))
            finish()
        }


    }

    fun hideKeyboard(view: View) {
        val view = this.currentFocus
        if (view != null){
            val hideMe = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            hideMe.hideSoftInputFromWindow(view.windowToken, 0)
        }
        else{
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN)
        }
    }

}
