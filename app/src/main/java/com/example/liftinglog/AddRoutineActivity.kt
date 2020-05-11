package com.example.liftinglog

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_routine.*

class AddRoutineActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    var exerciseList = mutableListOf<Exercise>(Exercise("Bench press", "notinging", 1, 0, mutableListOf(0), mutableListOf(0.0)))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_routine)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        loginUser()



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
            saveRoutine()

        }


    }

    fun saveRoutine(){
        val routine = Routine(routineNameText.text.toString(), exerciseList)

        println("!!! b4 nullcheck")
        val user = auth.currentUser ?: return
        println("!!! after nullcheck")

        db.collection("users").document(user.uid).collection("routines").add(routine)
            .addOnSuccessListener {documentReference ->
                println("!!! DocumentSnapshot added with ID: ${documentReference.id}")
                finish()
            }
            .addOnFailureListener{
                println("!!! error adding document: $it")
            }
    }

    fun loginUser(){
        if (auth.currentUser == null){
            auth.signInWithEmailAndPassword("test@test.com", "password")
                .addOnCompleteListener(this) { task ->
                    if (task.isSuccessful) {
                        println("!!! authloggedin")
                    } else {
                        println("!!! user not logged in")
                    }
                }
        } else {
            println("!!! user already logged in")
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
