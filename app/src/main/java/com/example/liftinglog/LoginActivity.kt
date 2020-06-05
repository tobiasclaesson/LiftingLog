package com.example.liftinglog

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    lateinit var emailEditText : EditText
    lateinit var passwordEditText : EditText
    lateinit var auth : FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        if (auth.currentUser != null){
            goToMainActivity()
        }
        if (auth.currentUser == null)
            println("!!! auth is null")


        emailEditText = findViewById<EditText>(R.id.emailEditText)
        passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        val signUpButton = findViewById<Button>(R.id.createUserButton)
        signUpButton.setOnClickListener{

        }

        val logInButton = findViewById<Button>(R.id.loginButton)
        logInButton.setOnClickListener{
            loginUser()
        }
        val createUserButton = findViewById<Button>(R.id.createUserButton)
        createUserButton.setOnClickListener{
            createUser()
        }

    }

    fun loginUser(){
        if (emailEditText.text.toString().isEmpty() || passwordEditText.text.toString().isEmpty()){
            Snackbar.make(emailEditText, "Fill in your Email and Password!", Snackbar.LENGTH_SHORT).show()
            return
        }

        if (auth.currentUser != null)
            return
        auth.signInWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    goToMainActivity()
                } else {
                    Snackbar.make(emailEditText, "User not found!", Snackbar.LENGTH_SHORT).show()
                    println("!!! user not logged in${task.exception}")
                }
            }

    }

    fun createUser() {
        if (emailEditText.text.toString().isEmpty() || passwordEditText.text.toString().isEmpty()){
            Snackbar.make(emailEditText, "Fill in your Email and Password!", Snackbar.LENGTH_SHORT).show()
            return
        }


        auth.createUserWithEmailAndPassword(emailEditText.text.toString(), passwordEditText.text.toString())
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    goToMainActivity()
                } else {
                    Snackbar.make(emailEditText, "User not created", Snackbar.LENGTH_SHORT).show()
                    println("!!! user not created ${emailEditText.text} ${passwordEditText.text} ${task.exception}")
                }
            }
    }

    fun goToMainActivity() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
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
