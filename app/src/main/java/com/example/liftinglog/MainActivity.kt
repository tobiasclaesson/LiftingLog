package com.example.liftinglog

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        loginUser()

        loadRoutines()



        DataManager.routines.add(Routine("My Test Routine", mutableListOf<Exercise>()))
        DataManager.routines.add(Routine("My Second Test Routine", mutableListOf<Exercise>()))

        val routinesRecyclerView = findViewById<RecyclerView>(R.id.routinesRecyclerView)
        val addRoutineFab = findViewById<FloatingActionButton>(R.id.addRoutineFab)

        routinesRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RoutineRecycleAdapter(this, DataManager.routines)
        routinesRecyclerView.adapter = adapter

        addRoutineFab.setOnClickListener {view ->
            val intent = Intent(this, AddRoutineActivity::class.java)
            startActivity(intent)
        }



    }

    override fun onResume() {
        super.onResume()


    }

    fun loadRoutines(){
        val user = auth.currentUser ?: return
        val routinesRef = db.collection("users").document(user.uid).collection("routines")

        routinesRef.addSnapshotListener { snapshot , e ->
            if(snapshot != null){
                DataManager.routines.clear()
                for (document in snapshot.documents){
                    val newRoutine = document.toObject(Routine::class.java)
                    if (newRoutine != null){
                        DataManager.routines.add(newRoutine!!)
                        routinesRecyclerView.adapter?.notifyDataSetChanged() // rätt?
                    }
                }
            }
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
