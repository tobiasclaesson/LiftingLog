package com.example.liftinglog

class Exercise(var name: String? = null,
               var note: String? = null,
               var set: Int = 1,
               var repss: Int = 0,
               var reps: MutableList<Int>? = null,
               var weight: MutableList<Double>? = null) {
    fun addSet(){
        reps!!.add(set, 0)
        weight!!.add(set, 0.0)
        set++

    }
    fun updateReps(set: Int, value: Int){
        this.reps!![set] = value
    }
    fun updateWeight(set: Int, value: Double){
        this.weight!![set] = value
    }
}
