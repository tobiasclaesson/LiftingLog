package com.example.liftinglog

class Exercise(var name: String? = null,
               var note: String? = null,
               var set: Int = 1,
               var reps: MutableList<Int>? = null,
               var weight: MutableList<Double>? = null) {
    fun addSet(){
        this.reps!!.add(set, 0)
        this.weight!!.add(set, 0.0)
        this.set++

    }

    fun removeSet(position: Int){
        if (this.reps!!.size > 1 && this.weight!!.size > 1) {
            this.reps!!.removeAt(position)
            this.weight!!.removeAt(position)
            this.set--
        }
    }

    fun updateReps(set: Int, value: Int){
        this.reps!![set] = value
    }
    fun updateWeight(set: Int, value: Double){
        this.weight!![set] = value
    }
}
