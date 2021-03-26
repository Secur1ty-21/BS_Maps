package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle

class Preview : Activity() {

    private var time:Int = 0
    
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        Thread{
            while (time <= 3){
                Thread.sleep(1000)
                time++
            }
            time = 0
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }.start()

    }
}