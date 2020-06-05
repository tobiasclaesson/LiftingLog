package com.example.liftinglog

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.fragment_routines.*

class MainActivity : AppCompatActivity() {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when(item.itemId){
            R.id.routinesBottomNav -> {
                replaceFragment(RoutinesFragment())
                this.title = "Routines"
                return@OnNavigationItemSelectedListener true
            }
            R.id.historyBottomNav -> {
                replaceFragment(HistoryFragment())
                this.title = "History"
                return@OnNavigationItemSelectedListener true
            }

        }
        false

    }

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        this.title = "Routines"
        replaceFragment(RoutinesFragment())
        auth = FirebaseAuth.getInstance()

        db = FirebaseFirestore.getInstance()
        bottomNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        /*
        loadRoutines()


        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottomNavigation)
        val routinesRecyclerView = findViewById<RecyclerView>(R.id.routinesRecyclerView)
        val addRoutineFab = findViewById<FloatingActionButton>(R.id.addRoutineFab)



        routinesRecyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = RoutineRecycleAdapter(this, DataManager.routines)
        routinesRecyclerView.adapter = adapter

        addRoutineFab.setOnClickListener {view ->
            val intent = Intent(this, AddRoutineActivity::class.java)
            startActivity(intent)
        }
        */
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.top_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logOutItem -> {
                DataManager.historyRoutines.clear()
                DataManager.routines.clear()
                auth.signOut()
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }


}
