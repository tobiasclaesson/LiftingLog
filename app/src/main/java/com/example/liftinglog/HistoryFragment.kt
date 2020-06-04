package com.example.liftinglog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import java.util.*
import java.util.concurrent.TimeUnit

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

lateinit var db: FirebaseFirestore
lateinit var auth: FirebaseAuth

/**
 * A simple [Fragment] subclass.
 * Use the [HistoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HistoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

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
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        val activity = activity as Context
        db = FirebaseFirestore.getInstance()
        auth = FirebaseAuth.getInstance()

        val historyRoutinesRecyclerView = view.findViewById<RecyclerView>(R.id.historyRoutinesRecyclerView)

        historyRoutinesRecyclerView.layoutManager = LinearLayoutManager(activity)
        val adapter = HistoryRoutineRecycleAdapter(activity, DataManager.historyRoutines)
        historyRoutinesRecyclerView.adapter = adapter

        loadRoutines(historyRoutinesRecyclerView)

        return view

    }

    fun loadRoutines(routinesRecyclerView: RecyclerView){
        val user = auth.currentUser ?: return
        val routinesRef = db.collection("users").document(user.uid).collection("finishedRoutines").orderBy("finishedDate", Query.Direction.DESCENDING)

        routinesRef.addSnapshotListener { snapshot , e ->
            if(snapshot != null){
                DataManager.historyRoutines.clear()
                for (document in snapshot.documents){
                    val newRoutine = document.toObject(Routine::class.java)
                    if (newRoutine != null){
                        newRoutine.docId = document.id
                        DataManager.historyRoutines.add(newRoutine)

                        routinesRecyclerView.adapter?.notifyDataSetChanged() // rätt?
                    }
                }
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
         * @return A new instance of fragment HistoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            HistoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
