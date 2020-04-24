package com.example.liftinglog

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class AddRoutineActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_routine)

        val routineNameText = findViewById<EditText>(R.id.routineNameText)


        routineNameText.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus){
                routineNameText.setText("")
            }
        }
    }

}
