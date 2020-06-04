package com.example.liftinglog

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class HistoryRoutineSummaryActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    lateinit var routine: Routine
    var exerciseList: MutableList<Exercise>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_history_routine_summary)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val routinePosition = intent.getIntExtra(HISTORY_ROUTINE_POSITION_KEY, POSITION_NOT_SET)
        routine = DataManager.historyRoutines[routinePosition]
        exerciseList = routine.exercises

        this.title = routine.name.toString()

        val historyRoutineSummaryRecyclerView = findViewById<RecyclerView>(R.id.historyRoutineSummaryRecyclerView)
        val historyRoutineAdapter = HistoryRoutineSummaryRecycleAdapter(this, exerciseList)
        historyRoutineSummaryRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRoutineSummaryRecyclerView.adapter = historyRoutineAdapter

        val continueButton = findViewById<Button>(R.id.continueButton)
        continueButton.setOnClickListener{
            finish()
        }

    }

    fun hideKeyboard(view: View){
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
