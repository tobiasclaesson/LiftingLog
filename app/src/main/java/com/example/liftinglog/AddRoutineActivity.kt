package com.example.liftinglog

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
    //private lateinit var detector: GestureDetectorCompat

    var exerciseList = mutableListOf<Exercise>(Exercise("Bench press", "notinging", 1,  mutableListOf(0), mutableListOf(0.0)))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_routine)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        loginUser()

        //detector = GestureDetectorCompat(this, MyGestureListener())

        val addExerciseButton = findViewById<Button>(R.id.addExerciseButton)
        val saveRoutineButton = findViewById<Button>(R.id.saveRoutineButton)
        val routineNameText = findViewById<EditText>(R.id.routineNameText)
        val addRoutineRecyclerView = findViewById<RecyclerView>(R.id.addRoutineRecyclerView)
        val addRoutineAdapter = AddRoutineRecycleAdapter(this, exerciseList)
        val setsRecyclerView = findViewById<RecyclerView>(R.id.addRoutineSetsRecyclerView)


        addRoutineRecyclerView.layoutManager = LinearLayoutManager(this)

        addRoutineRecyclerView.adapter = addRoutineAdapter



        routineNameText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus && routineNameText.text == null){
                routineNameText.setText("")
            }
        }
        addExerciseButton.setOnClickListener{ view ->
            exerciseList.add(Exercise("Dubbelhakspress", "hej", 1,  mutableListOf(0), mutableListOf(0.0)))
            addRoutineRecyclerView.adapter?.notifyDataSetChanged()
        }
        saveRoutineButton.setOnClickListener{ view ->
            saveRoutine()

        }


    }
/*
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (detector.onTouchEvent(event)){
            println("tru")
            true
        } else {
            println("super")
            super.onTouchEvent(event)
        }
    }

    inner class MyGestureListener : GestureDetector.SimpleOnGestureListener() {

        private val SWIPE_THRESHOLD = 100
        private val SWIPE_VELOCITY_THRESHHOLD = 100

        override fun onFling(
            downEvent: MotionEvent?,
            moveEvent: MotionEvent?,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            var diffX = moveEvent?.x?.minus(downEvent!!.x) ?: 0.0F
            var diffY = moveEvent?.y?.minus(downEvent!!.y) ?: 0.0F

            return if(abs(diffX) > abs(diffY)) {
                // horizontal swipe
                return if (abs(diffX) > SWIPE_THRESHOLD && abs(velocityX) > SWIPE_VELOCITY_THRESHHOLD){
                    if (diffX > 0){
                        // right swipe
                        this@AddRoutineActivity.onRightSwipe()
                    } else {
                        // left swipe
                        println("!!! leftswipeeeee")
                        this@AddRoutineActivity.onSwipeLeft()
                    }
                    true
                } else {
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }

            } else {
                // vertical swipe
                if (abs(diffY) > SWIPE_THRESHOLD && abs(velocityY) > SWIPE_VELOCITY_THRESHHOLD){
                    if (diffY > 0){
                        // up swipe
                        this@AddRoutineActivity.onDownSwipe()
                    } else {
                        // down swipe
                        this@AddRoutineActivity.onUpSwipe()
                    }
                    true
                } else {
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }

            }



        }
    }

    private fun onDownSwipe() {
        println("!!! downswipe")
    }

    private fun onUpSwipe() {
        println("!!! upswipe")
    }

    private fun onRightSwipe() {
        println("!!! rightswipe")
    }

    private fun onSwipeLeft() {
        Toast.makeText(this, "left swipe", Toast.LENGTH_LONG).show()
    }
*/
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
