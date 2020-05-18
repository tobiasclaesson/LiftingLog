package com.example.liftinglog

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import androidx.core.view.GestureDetectorCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_add_routine.*

const val POSITION_NOT_SET = -1
const val ROUTINE_POSITION_KEY = "STUDENT_POSITION"

class ActiveRoutineActivity : AppCompatActivity() {

    //private lateinit var detector: GestureDetectorCompat

    lateinit var routine: Routine
    var exerciseList: MutableList<Exercise>? = null

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_active_routine)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()
        loginUser()

        //detector = GestureDetectorCompat(this, MyGestureListener())
        val routinePosition = intent.getIntExtra(ROUTINE_POSITION_KEY, POSITION_NOT_SET)

        routine = DataManager.routines[routinePosition]
        exerciseList = routine.exercises

        this.title = routine.name.toString()

        val finishButton = findViewById<Button>(R.id.finishRoutineButton)
        val activeRoutineRecyclerView = findViewById<RecyclerView>(R.id.activeRoutineRecyclerView)
        val activeRoutineAdapter = ActiveRoutineRecycleAdapter(this, exerciseList)
        activeRoutineRecyclerView.layoutManager = LinearLayoutManager(this)
        activeRoutineRecyclerView.adapter = activeRoutineAdapter

        finishButton.setOnClickListener{view ->
            saveFinishedRoutine()
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

    fun saveFinishedRoutine(){

        println("!!! b4 nullcheck")
        val user = auth.currentUser ?: return
        println("!!! after nullcheck")

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

/*
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return if (detector.onTouchEvent(event)){
            true
        } else {
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

            return if(Math.abs(diffX) > Math.abs(diffY)) {
                // horizontal swipe
                return if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHHOLD){
                    if (diffX > 0){
                        // right swipe
                        this@ActiveRoutineActivity.onRightSwipe()
                    } else {
                        // left swipe
                        this@ActiveRoutineActivity.onSwipeLeft()
                    }
                    true
                } else {
                    super.onFling(downEvent, moveEvent, velocityX, velocityY)
                }

            } else {
                // vertical swipe
                if (Math.abs(diffY) > SWIPE_THRESHOLD && Math.abs(velocityY) > SWIPE_VELOCITY_THRESHHOLD){
                    if (diffY > 0){
                        // up swipe
                        this@ActiveRoutineActivity.onDownSwipe()
                    } else {
                        // down swipe
                        this@ActiveRoutineActivity.onUpSwipe()
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
        println("!!! leftswipe")
    }
*/
}
