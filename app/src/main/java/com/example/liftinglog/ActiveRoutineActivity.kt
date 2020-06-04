package com.example.liftinglog

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import java.util.concurrent.TimeUnit

const val POSITION_NOT_SET = -1
const val ROUTINE_POSITION_KEY = "ROUTINE_POSITION"

class ActiveRoutineActivity : AppCompatActivity() {



    lateinit var routine: Routine
    var exerciseList: MutableList<Exercise>? = null

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active_routine)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val date1 = Date()
        val date2 = Date()
        val diff: Long = date1.getTime() - date2.getTime()
        System.out.println("!!! Days: " + TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS))


        val routinePosition = intent.getIntExtra(ROUTINE_POSITION_KEY, POSITION_NOT_SET)

        routine = DataManager.routines[routinePosition]
        exerciseList = routine.exercises
        println("!!! routine document id: ${routine.docId}")
        this.title = routine.name.toString()

        val finishButton = findViewById<Button>(R.id.finishRoutineButton)
        val activeRoutineRecyclerView = findViewById<RecyclerView>(R.id.activeRoutineRecyclerView)
        val activeRoutineAdapter = ActiveRoutineRecycleAdapter(this, routine)
        activeRoutineRecyclerView.layoutManager = LinearLayoutManager(this)
        activeRoutineRecyclerView.adapter = activeRoutineAdapter

        finishButton.setOnClickListener{view ->
            updateCurrentRoutine()
            saveFinishedRoutine()
        }

    }

    fun updateCurrentRoutine(){
        val user = auth.currentUser ?: return



        db.collection("users").document(user.uid).collection("routines").document(routine.docId!!).set(routine)
            .addOnSuccessListener {documentReference ->
                //println("!!! DocumentSnapshot added with ID: ${documentReference.id}")
                finish()
            }
            .addOnFailureListener{
                println("!!! error adding document: $it")
            }
    }

    fun saveFinishedRoutine(){
        val user = auth.currentUser ?: return

        routine.finishedDate = Date()

        db.collection("users").document(user.uid).collection("finishedRoutines").add(routine)
            .addOnSuccessListener {documentReference ->
                println("!!! DocumentSnapshot added with ID: ${documentReference.id}")
                finish()
            }
            .addOnFailureListener{
                println("!!! error adding document: $it")
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
