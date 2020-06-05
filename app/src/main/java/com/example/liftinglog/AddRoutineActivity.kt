package com.example.liftinglog

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_routine.*
import kotlin.math.abs

class AddRoutineActivity : AppCompatActivity() {

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth
    lateinit var addRoutineRecyclerView: RecyclerView

    var exerciseList = mutableListOf<Exercise>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_routine)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        this.title = "New Routine"

        val addExerciseButton = findViewById<Button>(R.id.addExerciseButton)
        val saveRoutineButton = findViewById<Button>(R.id.saveRoutineButton)
        val routineNameText = findViewById<EditText>(R.id.routineNameText)
        addRoutineRecyclerView = findViewById<RecyclerView>(R.id.addRoutineRecyclerView)
        DataManager.newExercise.name = null
        val addRoutineAdapter = AddRoutineRecycleAdapter(this, exerciseList)

        addRoutineRecyclerView.layoutManager = LinearLayoutManager(this)

        addRoutineRecyclerView.adapter = addRoutineAdapter



        routineNameText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus && routineNameText.text == null){
                routineNameText.setText("")
            }
        }
        addExerciseButton.setOnClickListener{ view ->

            val intent = Intent(this, AddExerciseActivity::class.java)
            startActivity(intent)

        }
        saveRoutineButton.setOnClickListener{ view ->
            saveRoutine()

        }

    }

    override fun onResume() {
        super.onResume()

        val name = DataManager.newExercise.name ?: return
            exerciseList.add(DataManager.newExercise)
            addRoutineRecyclerView.adapter!!.notifyDataSetChanged()


    }

    fun saveRoutine(){
        val routine = Routine(routineNameText.text.toString(), null, exerciseList, "")

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
