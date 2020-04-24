package com.example.liftinglog

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var routines = listOf<Routine>(Routine("My Test Routine", listOf<Exercise>()), Routine("My Second Test Routine", listOf<Exercise>()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val routinesRecyclerView = findViewById<RecyclerView>(R.id.routinesRecyclerView)
        val addRoutineFab = findViewById<FloatingActionButton>(R.id.addRoutineFab)

        routinesRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RoutineRecycleAdapter(this, routines)
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
}
