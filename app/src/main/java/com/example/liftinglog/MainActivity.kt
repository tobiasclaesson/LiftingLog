package com.example.liftinglog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    var routines = listOf<Routine>(Routine("My Test Routine", listOf<Exercise>()), Routine("My Second Test Routine", listOf<Exercise>()))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val routinesRecyclerView = findViewById<RecyclerView>(R.id.routinesRecyclerView)

        routinesRecyclerView.layoutManager = LinearLayoutManager(this)

        val adapter = RoutineRecycleAdapter(this, routines)

        routinesRecyclerView.adapter = adapter
    }
}
