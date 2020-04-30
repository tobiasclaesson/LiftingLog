package com.example.liftinglog

class Exercise(var name: String,
               var note: String,
               var set: Int = 1,
               var repss: Int = 0,
               var reps: MutableList<Int>,
               var weight: MutableList<Double>) {
    fun addSet(){
        reps.add(set, 0)
        weight.add(set, 0.0)
        set++

    }
    fun updateReps(set: Int, value: Int){
        this.reps[set] = value
    }
    fun updateWeight(set: Int, value: Double){
        this.weight[set] = value
    }
}
