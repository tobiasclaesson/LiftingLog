package com.example.liftinglog

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_routines.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RoutinesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RoutinesFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var db: FirebaseFirestore
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val view = inflater.inflate(R.layout.fragment_routines, container, false)
        val activity = activity as Context


        val routinesRecyclerView = view.findViewById<RecyclerView>(R.id.routinesRecyclerView)
        val addRoutineFab = view.findViewById<FloatingActionButton>(R.id.addRoutineFab)
        val noRoutinesText = view.findViewById<TextView>(R.id.noRoutinesText)

        routinesRecyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = RoutineRecycleAdapter(activity, DataManager.routines, noRoutinesText, auth, db)
        routinesRecyclerView.adapter = adapter

        addRoutineFab.setOnClickListener {view ->
            val intent = Intent(activity, AddRoutineActivity::class.java)
            startActivity(intent)
        }

        loadRoutines(routinesRecyclerView)

        return view
    }

    fun loadRoutines(routinesRecyclerView: RecyclerView){
        val user = auth.currentUser ?: return
        val routinesRef = db.collection("users").document(user.uid).collection("routines")

        routinesRef.addSnapshotListener { snapshot , e ->

            if(snapshot != null){
                DataManager.routines.clear()
                for (document in snapshot.documents){
                    if(snapshot.documents.size > 0){
                    }
                    val newRoutine = document.toObject(Routine::class.java)
                    if (newRoutine != null){
                        newRoutine.docId = document.id
                        DataManager.routines.add(newRoutine)

                    }

                }
                routinesRecyclerView.adapter?.notifyDataSetChanged()
            }
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RoutinesFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RoutinesFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
