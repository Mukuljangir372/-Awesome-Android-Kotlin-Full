package com.mu.jan.problems

import android.view.View

class Tricks {
    /**
     * With() function in kotlin
     */
    inner class A(var name: String){}
    inner class B(var name: String){}
    private fun useOfWith(){
        val a = A("mukul")
        with(a){
            //Access object without dot operator
            name = ""
        }
    }
    /**
     * apply function in kotlin
     */
    private fun useOfApply(){
        A("name").apply {
            name = "new name"
        }
    }
    /**
     * infix function
     */
    private infix fun View.onClick(onClicked:(View)->Unit){
        setOnClickListener { onClicked(it) }
    }
    private fun useOfInfix(){
        //button onClick {}
    }



}