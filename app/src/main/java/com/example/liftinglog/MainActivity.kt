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
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        routinesRecyclerView.adapter?.notifyDataSetChanged()
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
